package com.sharework.response.model;

import com.sharework.response.model.meta.BasicMeta;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel
@Getter
@Setter
public class SuccessResponse {

    public SuccessResponse(BasicMeta meta) {
        this.meta = meta;
    }

    @ApiModelProperty(value = "meta", position = 1)
    private BasicMeta meta;

}
