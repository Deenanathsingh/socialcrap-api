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
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.socialcrap.api.common.constants.RestMappingConstants;
import com.socialcrap.api.common.constants.RestMappingConstants.SupportTicketConstants;
import com.socialcrap.api.common.enums.SocialCrapResponseCode;
import com.socialcrap.api.common.service.SupportTicketService;
import com.socialcrap.api.exception.SocialCrapException;
import com.socialcrap.api.model.request.dto.StatusUpdateRequest;
import com.socialcrap.api.model.request.dto.SupportTicketRequest;
import com.socialcrap.api.model.response.dto.BaseApiResponse;
import com.socialcrap.api.model.response.dto.PaginationResponse;
import com.socialcrap.api.model.response.dto.SupportTicketResponse;
import com.socialcrap.api.util.PaginationUtil;

@CrossOrigin
@RestController
@RequestMapping(value = RestMappingConstants.SupportTicketConstants.BASE)
public class SupportTicketController {

	@Autowired
	private SupportTicketService supportTicketService;

	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public PaginationResponse<List<SupportTicketResponse>> getAll(
			@RequestHeader(RestMappingConstants.TOKEN) String token, HttpServletRequest request,
			@RequestParam(required = false) String status,
			@RequestParam(required = false, defaultValue = "10") Integer limit,
			@RequestParam(required = false, defaultValue = "1") Integer offset) throws SocialCrapException {
		PaginationResponse<List<SupportTicketResponse>> response = supportTicketService.getAll(token, limit, offset,
				status);
		Long totalCount = supportTicketService.getTotalCount();
		PaginationUtil.setPageData(limit, offset, response, totalCount, request);
		return response;
	}

	@GetMapping(path = SupportTicketConstants.BY_USER)
	@ResponseStatus(HttpStatus.OK)
	public BaseApiResponse<List<SupportTicketResponse>> getAllByUserId(
			@PathVariable(RestMappingConstants.USER_ID) Long userId) throws SocialCrapException {
		List<SupportTicketResponse> responseList = supportTicketService.getBySentById(userId);
		if (CollectionUtils.isEmpty(responseList)) {
			return new BaseApiResponse<List<SupportTicketResponse>>(true,
					SocialCrapResponseCode.SUPPORT_TICKET_NOT_FOUND.getCode(), new ArrayList<>(),
					SocialCrapResponseCode.SUPPORT_TICKET_NOT_FOUND.getMessage());
		}
		BaseApiResponse<List<SupportTicketResponse>> response = new BaseApiResponse<List<SupportTicketResponse>>(
				responseList);
		return response;
	}

	@GetMapping(path = RestMappingConstants.ID_PARAM)
	@ResponseStatus(HttpStatus.OK)
	public BaseApiResponse<SupportTicketResponse> getById(@PathVariable(RestMappingConstants.ID) Long id)
			throws SocialCrapException {
		SupportTicketResponse response = supportTicketService.getById(id);
		if (response == null) {
			return new BaseApiResponse<>(true, SocialCrapResponseCode.SUPPORT_TICKET_ID_NOT_FOUND.getCode(), response,
					String.format(SocialCrapResponseCode.SUPPORT_TICKET_ID_NOT_FOUND.getMessage(), id));
		}
		return new BaseApiResponse<>(response);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.OK)
	public BaseApiResponse<Long> add(@Valid @RequestBody(required = true) SupportTicketRequest request)
			throws SocialCrapException {
		Long ticketId = supportTicketService.add(request);
		return new BaseApiResponse<Long>(ticketId);
	}

	@PatchMapping(path = SupportTicketConstants.UPDATE_STATUS)
	@ResponseStatus(HttpStatus.OK)
	public BaseApiResponse<Boolean> updateStatus(@Valid @RequestBody(required = true) StatusUpdateRequest request)
			throws SocialCrapException {
		supportTicketService.updateStatus(request);
		return new BaseApiResponse<Boolean>(true);
	}

}
