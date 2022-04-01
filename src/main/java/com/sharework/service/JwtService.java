package com.sharework.service;

import com.sharework.dao.UserDao;
import com.sharework.manager.JwtManager;
import com.sharework.manager.TokenIdentification;
import com.sharework.model.User;
import com.sharework.response.model.ErrorResponse;
import com.sharework.response.model.SignUpPayload;
import com.sharework.response.model.SignUpResponse;
import com.sharework.response.model.meta.BasicMeta;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class JwtService {

    @Autowired
    JwtManager jwtManager;
    @Autowired
    TokenIdentification identification;
    @Autowired
    UserDao userDao;

    public ResponseEntity reissuanceJwtToken(String accessToken, String refreshToken) {
        ResponseEntity response = null;
        ErrorResponse error = null;
        BasicMeta meta;
        if (accessToken == null && refreshToken == null) {
            String errorMsg = "토큰 확인바랍니다.";
            meta = new BasicMeta(false,errorMsg);
            error = new ErrorResponse(meta);
            response = new ResponseEntity(error, HttpStatus.OK);
        }
        HashMap<String, String> map = identification.getTokenUserid(accessToken, refreshToken);

        long userId = Long.parseLong(map.get("userid"));
        User user = userDao.getById(userId);
        //accessToken 생성
        JSONObject tokenParams = new JSONObject();
        tokenParams.put("userid", user.getId());
        tokenParams.put("usertype", user.getUserType());
        String newAccessToken = jwtManager.createAccessToken(tokenParams, 60 * 30);// 30분

        String newRefreshToken = refreshToken;
        if (map.get("refreshToken").equals("false")) {
            System.out.println("true");
            //refreshToken 생성
            newRefreshToken = jwtManager.createRefreshToken(60 * 60 * 24 * 7);// 일주일
            userDao.changeJwt(newRefreshToken, userId);
        }

        SignUpPayload signupPayload = new SignUpPayload(newAccessToken, newRefreshToken);
        final SignUpResponse result = new SignUpResponse(true, signupPayload);
        response = new ResponseEntity<>(result, HttpStatus.OK);
        return response;
    }
}
