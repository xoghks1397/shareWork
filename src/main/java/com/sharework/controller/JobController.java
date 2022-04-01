package com.sharework.controller;

import com.sharework.request.model.JobDetail;
import com.sharework.request.model.JobLocation;
import com.sharework.request.model.RegisterJob;
import com.sharework.response.model.ErrorResponse;
import com.sharework.response.model.Response;
import com.sharework.response.model.SuccessResponse;
import com.sharework.response.model.job.*;
import com.sharework.service.JobService;
import com.sharework.service.JobStatusService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = {"*"})
@RestController
@RequestMapping(path = "/api/v3/job")
public class JobController {

    @Autowired
    JobService jobService;

    @Autowired
    JobStatusService jobStatusService;

    @ApiResponses({@ApiResponse(code = 200, message = "SUCCESS", response = JobsResponse.class),
            @ApiResponse(code = 404, message = "NOT FOUND", response = ErrorResponse.class)})
    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(httpMethod = "GET", value = "위도경도 MAX,MIN값을 받아 해당 지역 JOB정보 넘겨줌", notes = "insert screean lat,lng information ")
    public ResponseEntity getJobList(JobLocation getJob) {
        return jobService.getJobList(getJob);
    }

    @ApiResponses({@ApiResponse(code = 200, message = "SUCCESS", response = SuccessResponse.class),
            @ApiResponse(code = 404, message = "NOT FOUND", response = ErrorResponse.class)})
    @PostMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(httpMethod = "POST", value = "일감 등록정보를 받아 DB에 저장", notes = "insert JOB information")
    public ResponseEntity insertJob(@RequestBody @ApiParam(required = true) RegisterJob job,
                                    @RequestHeader("access-token") String accessToken) {
        return jobService.insertJob(accessToken, job);
    }

    @ApiResponses({@ApiResponse(code = 200, message = "SUCCESS", response = JobClusterDetailResponse.class),
            @ApiResponse(code = 404, message = "NOT FOUND", response = ErrorResponse.class)})
    @GetMapping(value = "/Cluster", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(httpMethod = "GET", value = "클러스터에 해당하는 jobId와 page,pageSize 및 userLat,Lng를 받아 유저와의 거리순으로 job 상세정보 제공", notes = "insert jobId,pageNum,userLat,userLng")
    public ResponseEntity jobClusterDetail(JobDetail jobDetail) {
        return jobService.jobClusterDetail(jobDetail);
    }

    @ApiResponses({@ApiResponse(code = 200, message = "SUCCESS", response = APIJobDetail.class),
            @ApiResponse(code = 404, message = "NOT FOUND", response = Response.class)})
    @GetMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(httpMethod = "GET", value = "jobId를 받아 job정보 제공.", notes = "insert jobId")
    public ResponseEntity jobDetail(@PathVariable long id, @RequestHeader("access-token") String accessToken) {
        return jobService.jobDetail(id, accessToken);
    }

    @ApiResponses({@ApiResponse(code = 200, message = "SUCCESS", response = APIHiredList.class),
            @ApiResponse(code = 404, message = "NOT FOUND", response = Response.class)})
    @GetMapping(value = "hired/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(httpMethod = "GET", value = "채택한 회원 리스트")
    public ResponseEntity getHiredList(@PathVariable long id,
                                       @RequestHeader("access-token") String accessToken) {
        return jobService.getHiredList(id, accessToken);
    }

    @ApiResponses({@ApiResponse(code = 200, message = "SUCCESS", response = APIAppliedList.class),
            @ApiResponse(code = 404, message = "NOT FOUND", response = Response.class)})
    @GetMapping(value = "applied/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(httpMethod = "GET", value = "지원한 회원 리스트")
    public ResponseEntity getAppliedList(@PathVariable long id,
                                         @ApiParam(required = true) Integer page,
                                         @ApiParam(required = true) Integer size,
                                         @ApiParam(required = true) String orderBy,
                                         @RequestHeader("access-token") String accessToken) {
        return jobService.getAppliedList(id, page, size, orderBy, accessToken);
    }


    @ApiResponses({ @ApiResponse(code = 200, message = "SUCCESS", response = JobsResponse.class),
            @ApiResponse(code = 404, message = "NOT FOUND", response = ErrorResponse.class) })
    @GetMapping(value = "/status", produces = { MediaType.APPLICATION_JSON_VALUE })
    @ApiOperation(httpMethod = "GET", value = "회원별 진행중인 JOB정보 넘겨줌", notes = "return job list based on user")
    public ResponseEntity getJobList(@RequestHeader("access-token") String accessToken) {
        return jobStatusService.getJobList(accessToken);
    }
}
