package com.sharework.service;

import com.sharework.dao.LocationFavoriteDao;
import com.sharework.manager.TokenIdentification;
import com.sharework.model.LocationFavorite;
import com.sharework.response.model.LocationFavoriteResponse;
import com.sharework.response.model.SuccessResponse;
import com.sharework.response.model.location.LocationFavoritePayload;
import com.sharework.response.model.meta.BasicMeta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LocationFavoriteService {

    @Autowired
    LocationFavoriteDao locationFavoriteDao;
    @Autowired
    TokenIdentification  identification;

    public ResponseEntity getLocationFavoriteList(String accessToken) {
        ResponseEntity response = null;

        long userId = identification.getHeadertoken(accessToken);

        List<LocationFavorite> locationFavorites = locationFavoriteDao.findByUserId(userId);

        LocationFavoritePayload locationFavoritePayload = new LocationFavoritePayload(locationFavorites);
        BasicMeta meta = new BasicMeta(true, "");

        LocationFavoriteResponse locationFavoriteResponse = new LocationFavoriteResponse(true, locationFavoritePayload, meta);
        response = new ResponseEntity<>(locationFavoriteResponse, HttpStatus.OK);
        return response;
    }

    public ResponseEntity insertLocationFavorite(LocationFavorite locationFavorite, String accessToken) {
        ResponseEntity response = null;
        BasicMeta meta = new BasicMeta(true, "즐겨찾기가 성공적으로 저장되었습니다.");
        SuccessResponse result = new SuccessResponse(meta);

        long userId = identification.getHeadertoken(accessToken);

        int itemCount = locationFavoriteDao.countByUserId(userId);
        if (itemCount >= 5) {
            meta.setStatus(false);
            meta.setMessage("위치는 최대 5개까지 등록이 가능합니다.");
            result.setMeta(meta);
            response = new ResponseEntity<>(result, HttpStatus.OK);
            return response;
        }

        locationFavoriteDao.save(LocationFavorite.builder()
                .userId(userId)
                .locationName(locationFavorite.getLocationName())
                .lat(locationFavorite.getLat())
                .lng(locationFavorite.getLng())
                .build());
        result.setMeta(meta);
        response = new ResponseEntity<>(result, HttpStatus.OK);
        return response;
    }

    public ResponseEntity deleteLocationFavorite(long id) {
        ResponseEntity response = null;
        BasicMeta meta = new BasicMeta(true, "즐겨찾기가 성공적으로 삭제되었습니다.");

        locationFavoriteDao.deleteById(id);

        SuccessResponse result = new SuccessResponse(meta);
        result.setMeta(meta);
        response = new ResponseEntity<>(result, HttpStatus.OK);
        return response;
    }
}
