package com.sharework.response.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@ApiModel
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MainJobsResponse {

	@ApiModelProperty(value = "jobId", position = 1)
	private long jobId;
	@ApiModelProperty(value = "lng", position = 2)
	private double lng;
	@ApiModelProperty(value = "lat", position = 3)
	private double lat;

}
