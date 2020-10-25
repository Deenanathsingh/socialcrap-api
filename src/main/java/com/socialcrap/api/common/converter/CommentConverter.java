package com.socialcrap.api.common.converter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.util.StringUtils;

import com.socialcrap.api.model.entity.CommentEntity;
import com.socialcrap.api.model.request.dto.CommentRequest;
import com.socialcrap.api.model.response.dto.CommentResponse;

public class CommentConverter extends BaseConverter {

	public static CommentEntity getEntityFromRequest(CommentRequest request) {
		if (request == null) {
			return null;
		}
		CommentEntity response = new CommentEntity();
		response.setComment(request.getComment());
		setRequest(response, request);
		return response;
	}

	public static void updateData(CommentRequest request, CommentEntity entity) {
		if (request != null && entity != null) {
			if (!StringUtils.isEmpty(request.getComment())) {
				entity.setComment(request.getComment());
			}
		}
	}

	public static CommentResponse getResponseFromEntity(CommentEntity request) {
		if (request == null) {
			return null;
		}
		CommentResponse response = new CommentResponse();
		response.setId(request.getId());
		response.setComment(request.getComment());
		setResponse(request, response);
		return response;
	}

	public static List<CommentResponse> getResponseListFromEntityList(Collection<CommentEntity> requestList) {
		if (!CollectionUtils.isEmpty(requestList)) {
			return new ArrayList<>();
		}
		List<CommentResponse> responseList = new ArrayList<>();
		responseList = requestList.stream().map(request -> getResponseFromEntity(request)).collect(Collectors.toList());
		return responseList;
	}

}
