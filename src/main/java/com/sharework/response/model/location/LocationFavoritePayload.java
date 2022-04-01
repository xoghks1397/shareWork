package com.sharework.response.model.location;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

import com.sharework.model.LocationFavorite;

@ApiModel
@Getter
@Setter
@AllArgsConstructor
public class LocationFavoritePayload {

    @ApiModelProperty(value = "locationFavorites", position = 1)
    private List<LocationFavorite> locationFavorites;
}
