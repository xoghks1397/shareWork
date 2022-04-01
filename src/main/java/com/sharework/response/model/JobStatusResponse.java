package com.sharework.response.model;

import com.sharework.response.model.job.JobDetailPayload;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@ApiModel
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JobStatusResponse {

    private JobDetailPayload jobs;

    private Pagination pagination;

}