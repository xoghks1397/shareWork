package com.sharework.controller;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.sharework.model.User;
import com.sharework.request.model.SignupRequest;
import com.sharework.response.model.ErrorResponse;
import com.sharework.response.model.SignUpResponse;
import com.sharework.response.model.SuccessResponse;
import com.sharework.service.JwtService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@CrossOrigin(origins = { "*" })
@RestController
@RequestMapping(path = "/api/v3/jwt")
public class JwtController {

	@Autowired
	JwtService jwtService;

	@ApiResponses({ @ApiResponse(code = 200, message = "SUCCESS", response = SignUpResponse.class),
			@ApiResponse(code = 404, message = "NOT FOUND", response = ErrorResponse.class) })
	@PostMapping(produces = { MediaType.APPLICATION_JSON_VALUE })
	@ApiOperation(httpMethod = "POST", value = "jwt만료 시 jwt 재발급", notes = "Reissuance of jwt when jwt expires")
	public ResponseEntity reissuanceJwtToken(@RequestHeader("access-token") String accessToken,@RequestHeader("refresh-token") String refreshToken) {
		return jwtService.reissuanceJwtToken(accessToken,refreshToken);
	}
}
