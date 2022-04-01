package com.sharework.service;

import com.sharework.manager.CreateJwt;
import com.sharework.response.model.meta.BasicMeta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import com.sharework.dao.UserDao;
import com.sharework.manager.HashidsManager;
import com.sharework.manager.JwtManager;
import com.sharework.manager.TokenIdentification;
import com.sharework.model.ResidentNumberJsonb;
import com.sharework.model.User;
import com.sharework.request.model.SignupRequest;
import com.sharework.response.model.ErrorResponse;
import com.sharework.response.model.SignUpResponse;
import com.sharework.response.model.SignUpPayload;

@Service
public class UserService {
    @Autowired
    UserDao userDao;

    @Autowired
    JwtManager jwtManager;

    @Autowired
    HashidsManager hashidsManager;
    @Autowired
    TokenIdentification identification;
    @Autowired
    CreateJwt createJwt;

    public ResponseEntity signUp(SignupRequest request, BindingResult bindingResult) {

        ResponseEntity response = null;
        ErrorResponse error = null;
        String errorMsg = null;
        BasicMeta meta;

        // vaild가 틀렸을 경우
        if (bindingResult.hasErrors()) {
            errorMsg = "정확한 정보를 기입해주세요.";
            meta = new BasicMeta(false,errorMsg);
            error = new ErrorResponse(meta);
            response = new ResponseEntity<>(error, HttpStatus.OK);
            return response;
        } else if (userDao.getUserByEmail(request.getEmail()).isPresent()) {
            errorMsg = "이메일이 중복됩니다.";
            meta = new BasicMeta(false,errorMsg);
            error = new ErrorResponse(meta);
            response = new ResponseEntity<>(error, HttpStatus.OK);
            return response;
        } else if (userDao.findUserByPhoneNumber(request.getPhoneNumber()).isPresent()) {
            errorMsg = "번호가 중복됩니다.";
            meta = new BasicMeta(false,errorMsg);
            error = new ErrorResponse(meta);
            response = new ResponseEntity<>(error, HttpStatus.OK);
            return response;
        }
        ResidentNumberJsonb residentNumber = new ResidentNumberJsonb(request.getResidentNumberFront(),
                request.getResidentNumberRear());

        String refreshToken = createJwt.createRefreshToken();

        userDao.save(
                User.builder().email(request.getEmail()).name(request.getName()).phoneNumber(request.getPhoneNumber())
                        .residentNumber(residentNumber).userType("worker").refreshToken(refreshToken).build());

        String accessToken = createJwt.createAccessToken(userDao.getUserByPhoneNumber(request.getPhoneNumber()));

        SignUpPayload signupPayload = new SignUpPayload(accessToken, refreshToken);
        final SignUpResponse result = new SignUpResponse(true, signupPayload);
        response = new ResponseEntity<>(result, HttpStatus.OK);
        return response;
    }

}
