package com.socialcrap.api.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.socialcrap.api.common.constants.RestMappingConstants;
import com.socialcrap.api.common.enums.SocialCrapResponseCode;
import com.socialcrap.api.common.service.ChatService;
import com.socialcrap.api.exception.SocialCrapException;
import com.socialcrap.api.model.request.dto.ChatRequest;
import com.socialcrap.api.model.response.dto.BaseApiResponse;
import com.socialcrap.api.model.response.dto.ChatResponse;

@CrossOrigin
@RestController
@RequestMapping(value = RestMappingConstants.ChatConstants.BASE)
public class ChatController {

	@Autowired
	private ChatService chatService;

	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public BaseApiResponse<List<ChatResponse>> getById(
			@RequestParam(RestMappingConstants.ChatConstants.SENT_BY) Long sentBy,
			@RequestParam(RestMappingConstants.ChatConstants.SENT_TO) Long sentTo) {
		List<ChatResponse> responseList = chatService.getBySentByAndSentTo(sentTo, sentBy);
		if (CollectionUtils.isEmpty(responseList)) {
			return new BaseApiResponse<>(false, SocialCrapResponseCode.CHATS_NOT_FOUND.getCode(), new ArrayList<>(),
					SocialCrapResponseCode.CHATS_NOT_FOUND.getMessage());
		}
		return new BaseApiResponse<>(responseList);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.OK)
	public BaseApiResponse<Long> add(@Valid @RequestBody(required = true) ChatRequest request)
			throws SocialCrapException {
		if (request == null) {
			return new BaseApiResponse<>(0L);
		}
		Long response = chatService.add(request);
		return new BaseApiResponse<>(response);
	}

}
