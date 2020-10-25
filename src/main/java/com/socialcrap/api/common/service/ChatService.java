package com.socialcrap.api.common.service;

import java.util.List;

import com.socialcrap.api.model.request.dto.ChatRequest;
import com.socialcrap.api.model.response.dto.ChatResponse;

public interface ChatService extends AbstractService<ChatResponse, ChatRequest>{

	public List<ChatResponse> getBySentByAndSentTo(Long sentTo, Long sentBy);
	
}
