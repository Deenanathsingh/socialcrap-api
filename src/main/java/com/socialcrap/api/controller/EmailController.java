package com.socialcrap.api.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.socialcrap.api.common.constants.RestMappingConstants;
import com.socialcrap.api.common.service.EmailService;
import com.socialcrap.api.exception.SocialCrapException;
import com.socialcrap.api.model.request.dto.EmailRequest;
import com.socialcrap.api.model.response.dto.BaseApiResponse;
import com.socialcrap.api.model.response.dto.EmailResponse;
import com.socialcrap.api.model.response.dto.PaginationResponse;
import com.socialcrap.api.util.PaginationUtil;

@RestController
@RequestMapping(value = RestMappingConstants.EmailConstants.BASE)
public class EmailController {

	@Autowired
	private EmailService emailService;

	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public PaginationResponse<List<EmailResponse>> getAll(@RequestHeader(RestMappingConstants.TOKEN) String token,
			HttpServletRequest request, @RequestParam(required = false, defaultValue = "10") Integer limit,
			@RequestParam(required = false, defaultValue = "1") Integer offset) throws SocialCrapException {
		PaginationResponse<List<EmailResponse>> response = emailService.getAll(token, limit, offset);
		Long totalCount = emailService.getTotalCountOfAllByAdminId(token);
		PaginationUtil.setPageData(limit, offset, response, totalCount, request);
		return response;
	}

	@PostMapping
	@ResponseStatus(HttpStatus.OK)
	public BaseApiResponse<Boolean> sendEmail(@RequestHeader(RestMappingConstants.TOKEN) String token,
			@Valid @RequestBody EmailRequest request) throws SocialCrapException {
		Boolean response = emailService.sendEmail(token, request);
		return new BaseApiResponse<>(response);
	}

}
