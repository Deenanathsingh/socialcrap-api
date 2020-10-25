package com.socialcrap.api.common.converter;

import org.springframework.util.StringUtils;

import com.socialcrap.api.model.entity.ChatEntity;
import com.socialcrap.api.model.request.dto.ChatRequest;
import com.socialcrap.api.model.response.dto.ChatResponse;

public class ChatConverter extends BaseConverter {

	public static ChatEntity getEntityFromRequest(ChatRequest request) {
		if (request == null) {
			return null;
		}
		ChatEntity response = new ChatEntity();
		setRequest(response, request);
		response.setMessage(request.getMessage());
		return response;
	}

	public static void updateData(ChatRequest request, ChatEntity entity) {
		if (request != null && entity != null) {
			if (!StringUtils.isEmpty(request.getMessage())) {
				entity.setMessage(request.getMessage());
			}
		}
	}

	public static ChatResponse getResponseFromEntity(ChatEntity request) {
		if (request == null) {
			return null;
		}
		ChatResponse response = new ChatResponse();
		response.setMessage(request.getMessage());
		setResponse(request, response);
		return response;
	}

}
