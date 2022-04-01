package com.sharework.response.model;

import java.util.List;

import com.sharework.response.model.tag.giveTag;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@ApiModel
@Getter
@Setter
@AllArgsConstructor
public class giveTagListPayload {
	@ApiModelProperty(value = "tagSubList", position = 1)
	private List<giveTag> tagList;
}
