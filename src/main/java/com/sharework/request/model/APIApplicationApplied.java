package com.sharework.request.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ApiModel
public class APIApplicationApplied {
    @ApiModelProperty(value = "체크리스트 아이디 리스트")
    private List<Integer> applicationChecklistIds;

    @ApiModelProperty(value = "job id")
    private long jobId;

    @ApiModelProperty(example = "37.3", value = "유저 위도")
    private Double lat;

    @ApiModelProperty(example = "126.9", value = "유저 경도")
    private Double lng;

}
