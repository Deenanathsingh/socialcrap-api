package com.socialcrap.api.common.converter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.util.StringUtils;

import com.socialcrap.api.model.entity.Email;
import com.socialcrap.api.model.request.dto.EmailRequest;
import com.socialcrap.api.model.response.dto.EmailResponse;
import com.socialcrap.api.util.JsonUtil;

public class EmailConverter extends BaseConverter {

	public static Email getEntityFromRequest(EmailRequest request) {
		if (request == null) {
			return null;
		}
		Email response = new Email();
		if (!CollectionUtils.isEmpty(request.getTo())) {
			response.setReceiver(JsonUtil.getJson(request.getTo()));
		}
		if (!CollectionUtils.isEmpty(request.getCc())) {
			response.setCc(JsonUtil.getJson(request.getCc()));
		}
		if (!CollectionUtils.isEmpty(request.getBcc())) {
			response.setBcc(JsonUtil.getJson(request.getBcc()));
		}
		if (!CollectionUtils.isEmpty(request.getAttachments())) {
			response.setAttachments(JsonUtil.getJson(request.getAttachments()));
		}
		response.setSentBy(request.getUserId());
		response.setSubject(request.getSubject());
		response.setMessage(request.getMessage());
		setRequest(response, request);
		return response;
	}

	public static EmailResponse getResponseFromEntity(Email entity) {
		if (entity == null) {
			return null;
		}
		EmailResponse response = new EmailResponse();
		if (!StringUtils.isEmpty(entity.getReceiver())) {
			response.setTo(JsonUtil.convertToListPojo(entity.getReceiver(), String.class));
		}
		if (!StringUtils.isEmpty(entity.getCc())) {
			response.setCc(JsonUtil.convertToListPojo(entity.getCc(), String.class));
		}
		if (!StringUtils.isEmpty(entity.getBcc())) {
			response.setBcc(JsonUtil.convertToListPojo(entity.getBcc(), String.class));
		}
		if (!StringUtils.isEmpty(entity.getAttachments())) {
			response.setAttachments(JsonUtil.convertToListPojo(entity.getAttachments(), String.class));
		}
		response.setSubject(entity.getSubject());
		response.setMessage(entity.getMessage());
		setResponse(entity, response);
		return response;
	}

	public static List<EmailResponse> getResponseListFromEntityList(List<Email> entityList) {
		if (CollectionUtils.isEmpty(entityList)) {
			return null;
		}
		List<EmailResponse> responseList = new ArrayList<>();
		responseList = entityList.stream().map(r -> getResponseFromEntity(r)).collect(Collectors.toList());
		return responseList;
	}

}
