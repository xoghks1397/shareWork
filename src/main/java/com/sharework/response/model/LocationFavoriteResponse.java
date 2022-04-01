package com.sharework.response.model;

import com.sharework.response.model.location.LocationFavoritePayload;
import com.sharework.response.model.meta.BasicMeta;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel
@Getter
@Setter
public class LocationFavoriteResponse extends BasicResponse {

    public LocationFavoriteResponse(boolean status, LocationFavoritePayload payload, BasicMeta meta) {
        super(status);
        this.payload = payload;
        this.meta = meta;
    }

    @ApiModelProperty(value = "payload", position = 2)
    private LocationFavoritePayload payload;

    @ApiModelProperty(value = "meta", position = 3)
    private BasicMeta meta;
}
