package com.sharework.service;

import com.sharework.dao.JobDao;
import com.sharework.manager.TokenIdentification;
import com.sharework.model.Job;
import com.sharework.response.model.ErrorResponse;
import com.sharework.response.model.JobStatusResponse;
import com.sharework.response.model.job.JobDetailPayload;
import com.sharework.response.model.meta.BasicMeta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobStatusService {

    @Autowired
    JobDao jobDao;
    @Autowired
    TokenIdentification identification;


    public ResponseEntity getJobList(String token) {

        ResponseEntity response = null;
        ErrorResponse error = null;

        long id = identification.getHeadertoken(token);

        List<Job> jobList = jobDao.findJobsByUserId(id);

        BasicMeta meta;
        if (jobList.isEmpty()) {
            String errorMsg = "등록된 일감이 없습니다.";
            meta = new BasicMeta(false, errorMsg);
            error = new ErrorResponse(meta);
            return new ResponseEntity(error, HttpStatus.OK);
        }

        JobDetailPayload jobPayload = new JobDetailPayload(jobList);
//		Pagination pagination = new Pagination(jobList.isLast(), jobList.getTotalPages());
        final JobStatusResponse result = new JobStatusResponse(jobPayload, null);
        response = new ResponseEntity<>(result, HttpStatus.OK);
        return response;
    }


}