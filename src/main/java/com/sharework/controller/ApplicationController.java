package com.sharework.controller;

import com.sharework.request.model.APIApplicationApplied;
import com.sharework.response.model.Response;
import com.sharework.response.model.application.APIApplicationHistory;
import com.sharework.service.ApplicationSerive;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = {"*"})
@RestController
@RequestMapping(path = "/api/v3/application")
public class ApplicationController {

    @Autowired
    ApplicationSerive applicationService;

    @ApiResponses({@ApiResponse(code = 200, message = "SUCCESS", response = Response.class),
            @ApiResponse(code = 404, message = "NOT FOUND", response = Response.class)})
    @PostMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(httpMethod = "POST", value = "")
    public ResponseEntity insertApplication(@RequestBody @ApiParam(required = true) APIApplicationApplied application,
                                            @RequestHeader("access-token") String accessToken) {
        return applicationService.insertApplication(application, accessToken);
    }

    @ApiResponses({@ApiResponse(code = 200, message = "SUCCESS", response = APIApplicationHistory.class),
            @ApiResponse(code = 404, message = "NOT FOUND", response = Response.class)})
    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(httpMethod = "GET", value = "status별로 application list 호출")
    public ResponseEntity getApplicationList(@ApiParam(required = true) String status,
                                             @ApiParam(required = true) int page, @ApiParam(required = true) int size,
                                             @RequestHeader("access-token") String accessToken) {
        return applicationService.getApplicationList(status, page, size, accessToken);
    }

    @ApiResponses({@ApiResponse(code = 200, message = "SUCCESS", response = Response.class),
            @ApiResponse(code = 404, message = "NOT FOUND", response = Response.class)})
    @PatchMapping(value = "/hired-request/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(httpMethod = "PATCH", value = "업무 시작 요청")
    public ResponseEntity updateHiredRequest(@PathVariable long id,
                                             @ApiParam(required = true) Double userLat,
                                             @ApiParam(required = true) Double userLng,
                                             @RequestHeader("access-token") String accessToken) {
        return applicationService.updateHiredRequest(id, userLat, userLng, accessToken);
    }

    @ApiResponses({@ApiResponse(code = 200, message = "SUCCESS", response = Response.class),
            @ApiResponse(code = 404, message = "NOT FOUND", response = Response.class)})
    @PatchMapping(value = "/hired-approved/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(httpMethod = "PATCH", value = "업무 시작 승인")
    public ResponseEntity updateHiredApproved(@PathVariable long id,
                                              @RequestHeader("access-token") String accessToken) {
        return applicationService.updateHiredApproved(id, accessToken);
    }

    @ApiResponses({@ApiResponse(code = 200, message = "SUCCESS", response = Response.class),
            @ApiResponse(code = 404, message = "NOT FOUND", response = Response.class)})
    @PatchMapping(value = "/hired", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(httpMethod = "PATCH", value = "다중 채택하기")
    public ResponseEntity updateHired(@RequestBody List<Long> applicationIds,
                                      @RequestHeader("access-token") String accessToken) {
        return applicationService.updateHired(applicationIds, accessToken);
    }
}
