package com.socialcrap.api.common.converter;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.socialcrap.api.common.enums.HistoryStatus;
import com.socialcrap.api.model.entity.SupportTicket;
import com.socialcrap.api.model.request.dto.SupportTicketRequest;
import com.socialcrap.api.model.response.dto.SupportTicketResponse;
import com.socialcrap.api.util.JsonUtil;

public class SupportTicketConverter extends BaseConverter {

	public static SupportTicket getEntityFromRequest(SupportTicketRequest request) {
		if (request == null) {
			return null;
		}
		SupportTicket response = new SupportTicket();
		setRequest(response, request);
		response.setTitle(request.getTitle());
		response.setIssue(request.getIssue());
		response.setType(request.getType());
		if (!CollectionUtils.isEmpty(request.getAttachments())) {
			response.setAttachments(JsonUtil.getJson(request.getAttachments()));
		}
		response.setCurrentStatus(HistoryStatus.OPEN.getStatus());
		return response;
	}

	public static SupportTicketResponse getResponseFromEntity(SupportTicket request) {
		if (request == null) {
			return null;
		}
		SupportTicketResponse response = new SupportTicketResponse();
		response.setId(request.getId());
		response.setTitle(request.getTitle());
		response.setIssue(request.getIssue());
		response.setType(request.getType());
		if (!StringUtils.isEmpty(request.getAttachments())) {
			response.setAttachments(JsonUtil.convertToListPojo(request.getAttachments(), String.class));
		}
		setResponse(request, response);
		return response;
	}

	public static List<SupportTicketResponse> getResponseListFromEntityList(List<SupportTicket> requestList) {
		if (CollectionUtils.isEmpty(requestList)) {
			return null;
		}
		List<SupportTicketResponse> responseList = requestList.stream().map(r -> getResponseFromEntity(r))
				.collect(Collectors.toList());
		return responseList;
	}

}
