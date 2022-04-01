package com.sharework.service;

import com.sharework.common.ApplicationTypeEnum;
import com.sharework.common.JobTypeEnum;
import com.sharework.dao.ApplicationChecklistDao;
import com.sharework.dao.ApplicationDao;
import com.sharework.dao.JobDao;
import com.sharework.dao.JobTagDao;
import com.sharework.dao.UserDao;
import com.sharework.manager.TokenIdentification;
import com.sharework.model.*;
import com.sharework.request.model.APIApplicationApplied;
import com.sharework.response.model.*;
import com.sharework.response.model.application.APIApplicationHistory;
import com.sharework.response.model.meta.BasicMeta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ApplicationSerive {
	@Autowired
	ApplicationDao applicationDao;
	@Autowired
	ApplicationChecklistDao applicationChecklistDao;
	@Autowired
	JobDao jobDao;
	@Autowired
	JobTagDao jobTagDao;
	@Autowired
	UserDao userDao;
	@Autowired
	TokenIdentification identification;

	public ResponseEntity insertApplication(APIApplicationApplied application, String accessToken) {
		ResponseEntity response = null;
		Response error = null;

		long userId = identification.getHeadertoken(accessToken);

		// 이미 지원한 경우
		if (applicationDao.findByJobIdAndUserId(application.getJobId(), userId) != null) {
			String errorMsg = "이미 지원한 공고입니다.";
			error = new Response(new BasicMeta(false, errorMsg));
			response = new ResponseEntity<>(error, HttpStatus.OK);
			return response;
		}

		// application 저장
		Application insertApplication = applicationDao
			.save(Application.builder().userId(userId).jobId(application.getJobId()).lat(application.getLat())
					.lng(application.getLng()).status(ApplicationTypeEnum.APPLIED.name()).build());

		// 선택한 checklist 저장
		List<Integer> applicationChecklistIds = application.getApplicationChecklistIds();
		for (int checklistId : applicationChecklistIds) {
			applicationChecklistDao.save(ApplicationChecklist.builder()
					.applicationId(insertApplication.getId())
					.jobChecklistId(checklistId)
					.build());
		}

		response = new ResponseEntity<>(new Response(new BasicMeta(true, "")), HttpStatus.OK);
		return response;
	}

	public ResponseEntity getApplicationList(String status, int page, int size, String accessToken) {

		ResponseEntity response = null;
		Response error = null;

		long userId = identification.getHeadertoken(accessToken);

		PageRequest pageRequest = PageRequest.of(page, size);
		Page<Application> applications = applicationDao.getApplicationList(userId, status, pageRequest);

		List<APIApplicationHistory.Application> responseApplications = new ArrayList<>();
		for (Application application : applications) {

			Optional<Job> job = jobDao.findById(application.getJobId());

			if (status.equals(ApplicationTypeEnum.COMPLETED.name())) {
				// isReview check 로직

			}

			// 태그
			List<JobTag> tags = jobTagDao.findByJobId(application.getJobId());
			List<APIApplicationHistory.JobTag> responseTags = new ArrayList<>();
			for (JobTag tag : tags) {
				responseTags.add(new APIApplicationHistory.JobTag(tag.getContents()));
			}

			// user
			Optional<User> user = userDao.findById(job.get().getUserId());
			Optional<APIApplicationHistory.Users> responseUser = Optional.of(new APIApplicationHistory.Users(user.get().getProfileImg()));

			// job
			Optional<APIApplicationHistory.Job> responseJob = Optional.of(new APIApplicationHistory.Job(job.get().getTitle(),
					job.get().getStartAt(), job.get().getEndAt(), job.get().getPayType(), job.get().getPay(), responseUser.get(), responseTags));

			Optional<APIApplicationHistory.Application> responseApplication = Optional.of(new APIApplicationHistory.Application(
					application.getId(), application.getStatus(), responseJob.get(), true));

			responseApplications.add(responseApplication.get());
		}

		Pagination pagination = new Pagination(applications.isLast(), applications.getTotalPages());
		APIApplicationHistory.Payload payload = new APIApplicationHistory().new Payload(responseApplications, pagination);
		BasicMeta meta = new BasicMeta(true, "");

		response = new ResponseEntity<>(new APIApplicationHistory(payload, meta), HttpStatus.OK);
		return response;
	}

	public ResponseEntity updateHiredRequest(long id, Double userLat, Double userLng, String accessToken) {

		ResponseEntity response = null;
		Response error = null;

		long userId = identification.getHeadertoken(accessToken);

		Optional<Application> application = applicationDao.findById(id);
		Optional<Job> job = jobDao.findById(application.get().getJobId());

		if (job.get().getStatus().equals(JobTypeEnum.OPEN.name()) || job.get().getStatus().equals(JobTypeEnum.STARTED.name())) {
			application.get().setStatus(ApplicationTypeEnum.HIRED_REQUEST.name());
			applicationDao.save(application.get());

			error = new Response(new BasicMeta(true, ""));
		} else {
			String errorMsg = "업무가 종료된 공고입니다.";
			error = new Response(new BasicMeta(false, errorMsg));
		}

		response = new ResponseEntity<>(error, HttpStatus.OK);
		return response;
	}

	public ResponseEntity updateHiredApproved(long id, String accessToken) {

		ResponseEntity response = null;
		Response error = null;

		long userId = identification.getHeadertoken(accessToken);

		Optional<Application> application = applicationDao.findById(id);
		Optional<Job> job = jobDao.findById(application.get().getJobId());

		if (job.get().getStatus().equals(JobTypeEnum.OPEN.name()) || job.get().getStatus().equals(JobTypeEnum.STARTED.name())) {
			if (!application.get().getStatus().equals(ApplicationTypeEnum.HIRED_REQUEST.name())) {
				String errorMsg = "상태가 변경되었습니다.";
				error = new Response(new BasicMeta(false, errorMsg));
			} else {
				application.get().setStatus(ApplicationTypeEnum.HIRED_REQUEST.name());
				applicationDao.save(application.get());

				String message = "알바가 시작되었습니다.";
				error = new Response(new BasicMeta(true, message));
			}
		} else {
			String errorMsg = "업무가 종료되었습니다.";
			error = new Response(new BasicMeta(false, errorMsg));
		}

		response = new ResponseEntity<>(error, HttpStatus.OK);
		return response;
	}

	public ResponseEntity updateHired(List<Long> applicationIds, String accessToken) {

		ResponseEntity response = null;
		Response responseObj = null;

		for (Long id : applicationIds) {
			Optional<Application> application = applicationDao.findById(id);

			if (application.get().getStatus().equals(ApplicationTypeEnum.APPLIED.name())) {
				application.get().setStatus(ApplicationTypeEnum.HIRED.name());
				applicationDao.save(application.get());
			}
		}

		String message = "채택이 완료되었습니다.";
		responseObj = new Response(new BasicMeta(true, message));
		response = new ResponseEntity<>(responseObj, HttpStatus.OK);
		return response;
	}
}
