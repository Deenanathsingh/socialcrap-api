package com.socialcrap.api.common.converter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;

import com.socialcrap.api.model.entity.Notification;
import com.socialcrap.api.model.entity.User;
import com.socialcrap.api.model.response.dto.NotificationResponse;

public class NotificationConverter extends BaseConverter {

	public static NotificationResponse getResponseFromEntity(Notification request) {
		if (request == null) {
			return null;
		}
		NotificationResponse response = new NotificationResponse();
		response.setNotification(request.getNotification());
		response.setId(request.getId());
		setResponse(request, response);
		return response;
	}

	public static List<NotificationResponse> getResponseListFromEntityList(List<Notification> requestList) {
		if (CollectionUtils.isEmpty(requestList)) {
			return new ArrayList<>();
		}
		List<NotificationResponse> responseList = new ArrayList<>();
		responseList = requestList.stream().map(r -> getResponseFromEntity(r)).collect(Collectors.toList());
		return responseList;
	}

	public static Notification getEntityFromRequest(String message, User user) {
		Notification response = new Notification();
		response.setNotification(message);
		response.setSentTo(user);
		response.setActive(true);
		return response;
	}
}
