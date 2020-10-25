package com.socialcrap.api.common.converter;

import com.socialcrap.api.model.entity.StatusHistory;
import com.socialcrap.api.model.entity.SupportTicket;
import com.socialcrap.api.model.request.dto.StatusUpdateRequest;
import com.socialcrap.api.model.response.dto.StatusHistoryResponse;

public class StatusHistoryConverter extends BaseConverter {

	public static StatusHistory getEntityFromRequest(StatusUpdateRequest request) {
		if (request == null) {
			return null;
		}
		StatusHistory response = new StatusHistory();
		setRequest(response, request);
		response.setRemark(request.getRemark());
		response.setStatus(request.getStatus());
		return response;
	}
	
	public static StatusHistory getEntityFromTicket(SupportTicket request) {
		if (request == null) {
			return null;
		}
		StatusHistory response = new StatusHistory();
		response.setActive(true);
		response.setRemark(request.getIssue());
		response.setStatus(request.getCurrentStatus());
		response.setUpdatedBy(request.getSentBy());
		return response;
	}

	public static StatusHistoryResponse getResponseFromEntity(StatusHistory request) {
		if (request == null) {
			return null;
		}
		StatusHistoryResponse response = new StatusHistoryResponse();
		response.setId(request.getId());
		response.setRemark(request.getRemark());
		response.setStatus(request.getStatus());
		setResponse(request, response);
		return response;
	}

}
