package com.socialcrap.api.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.socialcrap.api.common.constants.RestMappingConstants;
import com.socialcrap.api.common.enums.SocialCrapResponseCode;
import com.socialcrap.api.common.service.NotificationService;
import com.socialcrap.api.exception.SocialCrapException;
import com.socialcrap.api.model.request.dto.NotificationRequest;
import com.socialcrap.api.model.response.dto.BaseApiResponse;
import com.socialcrap.api.model.response.dto.NotificationResponse;
import com.socialcrap.api.model.response.dto.PaginationResponse;
import com.socialcrap.api.util.PaginationUtil;

@CrossOrigin
@RestController
@RequestMapping(value = RestMappingConstants.NotificationConstants.BASE)
public class NotificationController {

	@Autowired
	private NotificationService notificationService;

	@GetMapping(path = RestMappingConstants.NotificationConstants.CLEAR_ALL + RestMappingConstants.USER_ID_PARAM)
	@ResponseStatus(HttpStatus.OK)
	public BaseApiResponse<Boolean> clearAll(@PathVariable(RestMappingConstants.USER_ID) Long userId)
			throws SocialCrapException {
		notificationService.deactivateAllByUserId(userId);
		return new BaseApiResponse<>(true);
	}

	@GetMapping(path = RestMappingConstants.USER_ID_PARAM)
	@ResponseStatus(HttpStatus.OK)
	public BaseApiResponse<List<NotificationResponse>> getAllByUserId(
			@PathVariable(RestMappingConstants.USER_ID) Long userId) throws SocialCrapException {
		List<NotificationResponse> response = notificationService.getAllByUserId(userId);
		if (CollectionUtils.isEmpty(response)) {
			return new BaseApiResponse<>(false, SocialCrapResponseCode.NOTIFICTION_NOT_FOUND.getCode(),
					new ArrayList<>(), SocialCrapResponseCode.NOTIFICTION_NOT_FOUND.getMessage());
		}
		return new BaseApiResponse<>(response);
	}

	@GetMapping(path = RestMappingConstants.NotificationConstants.ACTIVE + RestMappingConstants.USER_ID_PARAM)
	@ResponseStatus(HttpStatus.OK)
	public BaseApiResponse<List<NotificationResponse>> getAllActiveByUserId(
			@PathVariable(RestMappingConstants.USER_ID) Long userId) throws SocialCrapException {
		List<NotificationResponse> response = notificationService.getAllActiveByUserId(userId);
		if (CollectionUtils.isEmpty(response)) {
			return new BaseApiResponse<>(false, SocialCrapResponseCode.NOTIFICTION_NOT_FOUND.getCode(),
					new ArrayList<>(), SocialCrapResponseCode.NOTIFICTION_NOT_FOUND.getMessage());
		}
		return new BaseApiResponse<>(response);
	}

	@GetMapping(path=RestMappingConstants.NotificationConstants.BY_ADMIN)
	@ResponseStatus(HttpStatus.OK)
	public PaginationResponse<List<NotificationResponse>> getAllSentByAdmin(
			@RequestHeader(RestMappingConstants.TOKEN) String token, HttpServletRequest request,
			@RequestParam(required = false, defaultValue = "10") Integer limit,
			@RequestParam(required = false, defaultValue = "1") Integer offset) throws SocialCrapException {
		PaginationResponse<List<NotificationResponse>> response = notificationService.getAllByAdmin(token, limit,
				offset);
		Long totalCount = notificationService.getTotalCountOfAllByAdminToken(token);
		PaginationUtil.setPageData(limit, offset, response, totalCount, request);
		return response;
	}

	@PostMapping
	@ResponseStatus(HttpStatus.OK)
	public BaseApiResponse<Boolean> sentNotification(@RequestHeader(RestMappingConstants.TOKEN) String token,
			@Valid @RequestBody NotificationRequest request) throws SocialCrapException {
		Boolean response = notificationService.sendNotification(request, token);
		return new BaseApiResponse<Boolean>(response);
	}

}
