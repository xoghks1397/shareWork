package com.sharework.controller;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.sharework.request.model.SignupRequest;
import com.sharework.response.model.ErrorResponse;
import com.sharework.response.model.SignUpResponse;
import com.sharework.service.UserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@CrossOrigin(origins = { "*" })
@RestController
@RequestMapping(path = "/api/v3/registration")
public class UserController {
	@Autowired
	UserService userService;

	@ApiResponses({ @ApiResponse(code = 200, message = "SUCCESS", response = SignUpResponse.class),
			@ApiResponse(code = 404, message = "NOT FOUND", response = ErrorResponse.class) })
	@PostMapping(produces = { MediaType.APPLICATION_JSON_VALUE })
	@ApiOperation(httpMethod = "POST", value = "회원정보를 받아 회원가입 진행(access-token,refresh-token 발행)", notes = "insert user infomation")
	public ResponseEntity signUp(@Valid @RequestBody(required = true) SignupRequest request,
			BindingResult bindingResult) {
		return userService.signUp(request, bindingResult);
	}
}
