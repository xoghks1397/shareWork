package com.sharework.response.model.job;

import java.util.List;

import com.sharework.model.Job;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@ApiModel
@Getter
@Setter
@AllArgsConstructor
public class JobClusterDetailPayload {
	@ApiModelProperty(value = "jobs", position = 1)
	private List<JobClusterDetailJob> Jobs;

}
