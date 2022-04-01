package com.sharework.request.model;

import javax.persistence.Column;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Valid
@ToString

public class RegisterJob {
	@ApiModelProperty(value = "title", position = 1, example = "강남역이사도와주실한분구함", required = true)
	@NotNull
	private String title;

	@ApiModelProperty(value = "address", position = 2, example = "서울강남구삼성동47-26", required = true)
	@NotNull
	private String address;

	@ApiModelProperty(value = "addressDetail", position = 3, example = "우봉빌라201호")
	private String addressDetail;

	@ApiModelProperty(value = "dateAt", position = 4, example = "2021-02-20", required = true)
	@NotNull
	private String dateAt;

	@ApiModelProperty(value = "startAt", position = 5, example = "09:00:00", required = true)
	@NotNull
	private String startAt;

	@ApiModelProperty(value = "endAt", position = 6, example = "18:00:00", required = true)
	@NotNull
	private String endAt;

	@ApiModelProperty(value = "lat", position = 7, example = "37.9", required = true)
	@NotNull
	private double lat;

	@ApiModelProperty(value = "lng", position = 8, example = "126.9", required = true)
	@NotNull
	private double lng;

	@ApiModelProperty(value = "personnel", position = 9, example = "1", required = true)
	private long personnel;

	@ApiModelProperty(value = "payType", position = 10, example = "시급", required = true)
	private String payType;

	@ApiModelProperty(value = "pay", position = 11, example = "10000")
	private int pay;

	@ApiModelProperty(value = "contents", position = 12, example = "개꿀임", required = true)
	@NotNull
	private String contents;

	@ApiModelProperty(value = "status", position = 13, example = "구직중", required = true)
	@NotNull
	@Column(name = "status")
	private String status;

	@ApiModelProperty(value = "checkList", position = 13, example = "[43,44,45,46]", required = true)
	@NotNull
	private long[] checkList;

	@ApiModelProperty(value = "tagSubList", position = 14, example = "[7,10,15,20]", required = true)
	@NotNull
	private long[] tagSubList;
}
