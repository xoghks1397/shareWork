package com.sharework.response.model.job;

import com.sharework.response.model.BasicResponse;
import com.sharework.response.model.meta.BasicMeta;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel
@Getter
@Setter
public class JobClusterDetailResponse {

    public JobClusterDetailResponse(JobClusterDetailPagination pagination, JobClusterDetailPayload payload, BasicMeta meta) {

        this.payload = payload;
        this.pagination = pagination;
        this.meta = meta;
    }

    @ApiModelProperty(value = "pagination", position = 1)
    private JobClusterDetailPagination pagination;

    @ApiModelProperty(value = "payload", position = 2)
    private JobClusterDetailPayload payload;

    @ApiModelProperty(value = "meta", position = 3)
    private BasicMeta meta;


}
