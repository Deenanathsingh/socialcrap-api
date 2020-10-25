package com.socialcrap.api.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.socialcrap.api.common.constants.RestMappingConstants;
import com.socialcrap.api.common.service.UserService;
import com.socialcrap.api.exception.SocialCrapException;
import com.socialcrap.api.model.request.dto.LoginRequest;
import com.socialcrap.api.model.request.dto.UserRequest;
import com.socialcrap.api.model.response.dto.BaseApiResponse;
import com.socialcrap.api.model.response.dto.UserLoginResponse;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping(value = RestMappingConstants.AdminConstants.BASE)
public class AdminController {
	
	@Autowired
	private UserService userService;
	
	@PostMapping(path = RestMappingConstants.AdminConstants.LOGIN)
	public BaseApiResponse<UserLoginResponse> login(@Valid @RequestBody(required = true) LoginRequest request)
			throws SocialCrapException {
		UserLoginResponse response = userService.loginAdmin(request);
		return new BaseApiResponse<UserLoginResponse>(response);
	}
	
	@PostMapping
	public BaseApiResponse<Long> add(@Valid @RequestBody(required = true) UserRequest request)
			throws SocialCrapException {
		Long userId = userService.addAdmin(request);
		return new BaseApiResponse<Long>(userId);
	}

}
