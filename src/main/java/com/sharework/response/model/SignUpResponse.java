package com.sharework.response.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel
@Getter
@Setter
public class SignUpResponse extends BasicResponse {
	public SignUpResponse(boolean status, SignUpPayload payload) {
		super(status);
		this.payload = payload;
	}

	@ApiModelProperty(value = "payload", position = 2)
	private SignUpPayload payload;
}
