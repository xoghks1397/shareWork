package com.sharework.service;

import com.sharework.base.model.BaseBenefit;
import com.sharework.common.ApplicationTypeEnum;
import com.sharework.common.JobTypeEnum;
import com.sharework.dao.*;
import com.sharework.manager.TokenIdentification;
import com.sharework.model.*;
import com.sharework.request.model.JobLocation;
import com.sharework.request.model.JobDetail;
import com.sharework.request.model.RegisterJob;
import com.sharework.response.model.*;
import com.sharework.response.model.job.*;
import com.sharework.response.model.meta.BasicMeta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.sharework.response.model.user.userProfileImg;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class JobService {

    @Autowired
    JobDao jobDao;
    @Autowired
    ApplicationDao applicationDao;
    @Autowired
    UserChecklistDao userChecklistDao;
    @Autowired
    TokenIdentification identification;
    @Autowired
    TagService tagService;
    @Autowired
    ChecklistService userChecklistService;
    @Autowired
    UserDao userDao;
    @Autowired
    JobBenefitDao jobBenefitDao;
    @Autowired
    JobTagDao jobTagDao;
    @Autowired
    JobCheckListDao jobCheckListDao;
    @Autowired
    ApplicationChecklistDao applicationChecklistDao;
    @Autowired
    BaseBenefitDao baseBenefitDao;

    public ResponseEntity getJobList(JobLocation getJob) {

        ResponseEntity response = null;
        ErrorResponse error = null;
        List<Job> jobList = jobDao.selectJobs(getJob.getSouthwestLat(), getJob.getNortheastLat(),
                getJob.getSouthwestLng(), getJob.getNortheastLng());
        BasicMeta meta;
        if (jobList.isEmpty()) {
            String errorMsg = "등록된 일감이 없습니다.";
            meta = new BasicMeta(false, errorMsg);
            error = new ErrorResponse(meta);
            response = new ResponseEntity<>(error, HttpStatus.OK);
            return response;
        }

        List<MainJobsResponse> mainJobsResponse = new ArrayList<MainJobsResponse>();

        for (Job job : jobList)
            mainJobsResponse.add(
                    new MainJobsResponse(job.getId(), job.getLng(), job.getLat()));

        JobsPayload jobPayload = new JobsPayload(mainJobsResponse);
        meta = new BasicMeta(true, "공고 목록을 성공적으로 전달하였습니다.");
        final JobsResponse result = new JobsResponse(jobPayload, meta);
        response = new ResponseEntity<>(result, HttpStatus.OK);
        return response;
    }

    public ResponseEntity insertJob(String accessToken, RegisterJob registerJob) {
        ResponseEntity response = null;
        ErrorResponse error = null;
        BasicMeta meta;
        long id = identification.getHeadertoken(accessToken);

        // 시작 끝 날짜 localtime으로 변경 후 저장(끝나는 시간이 시작보다 작을 시 끝나는 시간은 다음날로 변경)
        String startAt = registerJob.getDateAt() + " " + registerJob.getStartAt();
        String endAt = registerJob.getDateAt() + " " + registerJob.getEndAt();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime startTime = LocalDateTime.parse(startAt, formatter);
        LocalDateTime endTime = LocalDateTime.parse(endAt, formatter);


        if (startTime.isAfter(endTime)) {
            endTime = endTime.plusDays(1);
        }

        // job저장
        Job job = jobDao.save(Job.builder().userId(id).title(registerJob.getTitle()).startAt(startTime).endAt(endTime)
                .lat(registerJob.getLat()).lng(registerJob.getLng()).address(registerJob.getAddress())
                .addressDetail(registerJob.getAddressDetail()).personnel(registerJob.getPersonnel())
                .payType(registerJob.getPayType()).pay(registerJob.getPay()).contents(registerJob.getContents())
                .status(registerJob.getStatus()).build());

        // tag 저장
        long jobId = job.getId();
        if (!tagService.insertJobTag(registerJob.getTagSubList(), jobId)) {
            String errorMsg = "잘못된 태그입니다.";
            meta = new BasicMeta(false, errorMsg);
            error = new ErrorResponse(meta);
            response = new ResponseEntity<>(error, HttpStatus.OK);
            return response;
        }

        // checkList 저장
        if (!userChecklistService.insertJobCheckList(registerJob.getCheckList(), jobId, id)) {
            String errorMsg = "잘못된 체크리스트입니다.";
            meta = new BasicMeta(false, errorMsg);
            error = new ErrorResponse(meta);
            response = new ResponseEntity<>(error, HttpStatus.OK);
            return response;
        }
        meta = new BasicMeta(true, "공고가 성공적으로 저장되었습니다.");
        SuccessResponse result = new SuccessResponse(meta);
        response = new ResponseEntity<>(result, HttpStatus.OK);
        return response;
    }

    public ResponseEntity jobClusterDetail(JobDetail jobDetail) {

        ResponseEntity response = null;
        ErrorResponse error = null;
        PageRequest pageRequest = PageRequest.of(jobDetail.getPage(), jobDetail.getPageSize());
        Page<Job> job = jobDao.findJobsDetail(jobDetail.getJobIds(), pageRequest);


        List<JobClusterDetailJob> jobClusterList = new ArrayList<JobClusterDetailJob>();
        userProfileImg user = new userProfileImg(null);
        List<JobTagContents> jobTags = new ArrayList<JobTagContents>();

        for (int i = 0; i < job.getContent().size(); i++) {
            Job getJob = job.getContent().get(i);
            JobClusterDetailJob jobCluster = new JobClusterDetailJob();
            jobClusterList.add(jobCluster.builder().id(getJob.getId()).title(getJob.getTitle()).startAt(getJob.getStartAt())
                    .endAt(getJob.getEndAt()).payType(getJob.getPayType()).pay(getJob.getPay()).contents(getJob.getContents())
                    .distance(null).user(user).jobTags(jobTags).build());
        }
        JobClusterDetailPayload payload = new JobClusterDetailPayload(jobClusterList);
        JobClusterDetailPagination pagination = new JobClusterDetailPagination(job.isLast(), job.getTotalPages(), jobDetail.getPage());
        BasicMeta meta = new BasicMeta(true,"");
        final JobClusterDetailResponse result = new JobClusterDetailResponse(pagination,payload,meta);
        response = new ResponseEntity<>(result, HttpStatus.OK);
        return response;
    }

    public ResponseEntity jobDetail(long id, String accessToken) {
        ResponseEntity response = null;
        Response error = null;

        long userId = identification.getHeadertoken(accessToken);

        Optional<Job> job = jobDao.findById(id);

        if (job.isEmpty()) {
            String errorMsg = "등록된 일감이 없습니다.";
            error = new Response(new BasicMeta(false, errorMsg));
            response = new ResponseEntity<>(error, HttpStatus.OK);
            return response;
        }

        if (!job.get().getStatus().equals(JobTypeEnum.OPEN.name())) {
            String errorMsg = "마감된 공고 입니다.";
            error = new Response(new BasicMeta(false, errorMsg));
            response = new ResponseEntity<>(error, HttpStatus.OK);
            return response;
        }

        // giver user
        Optional<User> user = userDao.findById(job.get().getUserId());
        Optional<APIJobDetail.User> responseUser = Optional.of(new APIJobDetail.User(user.get().getName(), user.get().getProfileImg()));

        // 제공사항
        List<JobBenefit> benefits = jobBenefitDao.findByJobId(id);
        List<APIJobDetail.JobBenefit> responseBenefits = new ArrayList<>();
        for (JobBenefit benefit : benefits) {
//            benefit.getBaseBenefitId()
            Optional<BaseBenefit> baseJobBenefit = baseBenefitDao.findById(benefit.getBaseBenefitId());
            responseBenefits.add(new APIJobDetail.JobBenefit(baseJobBenefit.get().getContents()));
        }

        // 태그
        List<JobTag> tags = jobTagDao.findByJobId(id);
        List<APIJobDetail.JobTag> responseTags = new ArrayList<>();
        for (JobTag tag : tags) {
            responseTags.add(new APIJobDetail.JobTag(tag.getId(), tag.getContents()));
        }

        // 체크리스트
        List<JobCheckList> checkLists = jobCheckListDao.findByJobId(id);
        List<APIJobDetail.JobChecklist> responseChecklists = new ArrayList<>();
        for (JobCheckList checkList : checkLists) {
            Optional<UserChecklist> userChecklist = userChecklistDao.findByid(checkList.getUserCheckListId());
            responseChecklists.add(new APIJobDetail.JobChecklist(userChecklist.get().getId(), userChecklist.get().getContents()));
        }

        Optional<APIJobDetail.Job> responseJob = Optional.of(new APIJobDetail.Job(job.get().getId(), job.get().getTitle(), job.get().getStartAt(),
                job.get().getEndAt(), job.get().getLat(), job.get().getLng(), job.get().getAddress(), job.get().getAddressDetail(),
                job.get().getPersonnel(), job.get().getPayType(), job.get().getPay(), job.get().getContents(), job.get().getStatus(),
                responseUser.get(), responseBenefits, responseTags, responseChecklists));

        Application application = applicationDao.findByJobIdAndUserId(job.get().getId(), userId);
        boolean isCanApplied;
        isCanApplied = application != null;

        APIJobDetail.Payload payload = new APIJobDetail.Payload(responseJob.get());
        APIJobDetail.Meta meta = new APIJobDetail.Meta(true, "", isCanApplied);

        response = new ResponseEntity<>(new APIJobDetail(payload, meta), HttpStatus.OK);
        return response;
    }

    public ResponseEntity getHiredList(long id, String accessToken) {
        ResponseEntity response = null;
        Response error = null;

        long userId = identification.getHeadertoken(accessToken);

        List<Application> applications = applicationDao.findByJobIdAndStatus(id, ApplicationTypeEnum.HIRED.name());

        List<APIHiredList.Application> responseApplications = new ArrayList<>();
        for (Application application : applications) {

            // user
            Optional<User> user = userDao.findById(application.getUserId());
            Optional<APIHiredList.User> responseUser = Optional.of(new APIHiredList.User(user.get().getId(), user.get().getName(), user.get().getProfileImg()));

            Boolean isAction = false;
            if (application.getStatus().equals(ApplicationTypeEnum.HIRED.name())) {
                isAction = true;
            }

            Optional<APIHiredList.Application> responseApplication = Optional.of(new APIHiredList.Application(
                    application.getId(), application.getStartAt(), application.getEndAt(), application.getStatus(), isAction, responseUser.get()));

            responseApplications.add(responseApplication.get());
        }

        APIHiredList.Payload payload = new APIHiredList().new Payload(responseApplications);
        APIHiredList.Meta meta = new APIHiredList.Meta(true, "", applications.size());

        response = new ResponseEntity<>(new APIHiredList(payload, meta), HttpStatus.OK);
        return response;
    }

    public ResponseEntity getAppliedList(long id, Integer page, Integer size, String orderBy, String accessToken) {
        ResponseEntity response = null;
        Response error = null;

        long userId = identification.getHeadertoken(accessToken);

        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Application> applications = applicationDao.findByJobIdAndApplied(id, ApplicationTypeEnum.APPLIED.name(), pageRequest);

        List<APIAppliedList.Application> responseApplications = new ArrayList<>();
        List<JobCheckList> jobCheckLists = jobCheckListDao.findByJobId(id);
        for (Application application : applications) {

            // user
            Optional<User> user = userDao.findById(application.getUserId());

            int completed = applicationDao.countByUserIdAndStatus(user.get().getId(), ApplicationTypeEnum.COMPLETED.name()); // 경력
            int noShow = applicationDao.countByUserIdAndStatus(user.get().getId(), ApplicationTypeEnum.NO_SHOW.name()); // 결근

            Optional<APIAppliedList.User> responseUser = Optional.of(new APIAppliedList.User(user.get().getId(), user.get().getName(),
                    user.get().getProfileImg(), completed, noShow));

            // 지원서 체크리스트
            List<ApplicationChecklist> applicationChecklists = applicationChecklistDao.findByApplicationId(application.getId());
            List<APIAppliedList.ApplicationChecklist> responseApplicationChecklists = new ArrayList<>();
            for (JobCheckList jobCheckList : jobCheckLists) {
                boolean isResponse = false;
                for (ApplicationChecklist applicationChecklist : applicationChecklists) {
                    if (jobCheckListDao.findById(applicationChecklist.getJobChecklistId()).isPresent()) {
                        isResponse = true;
                        break;
                    }
                }

                Optional<UserChecklist> userChecklist = userChecklistDao.findByid(jobCheckList.getUserCheckListId());
                responseApplicationChecklists.add(new APIAppliedList.ApplicationChecklist(userChecklist.get().getContents(), isResponse));
            }

            Optional<APIAppliedList.Application> responseApplication = Optional.of(new APIAppliedList.Application(
                    application.getId(), application.getStatus(), responseUser.get(), responseApplicationChecklists));

            responseApplications.add(responseApplication.get());
        }

        Pagination pagination = new Pagination(applications.isLast(), applications.getTotalPages());
        APIAppliedList.Payload payload = new APIAppliedList().new Payload(responseApplications, pagination);
        APIAppliedList.Meta meta = new APIAppliedList.Meta(true, "", applications.getSize());

        response = new ResponseEntity<>(new APIAppliedList(payload, meta), HttpStatus.OK);
        return response;
    }
}
