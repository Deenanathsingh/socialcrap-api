package com.socialcrap.api.common.converter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.util.StringUtils;

import com.socialcrap.api.model.entity.Upload;
import com.socialcrap.api.model.pojo.UploadPojo;
import com.socialcrap.api.model.response.dto.UploadResponse;

public class UploadConverter extends BaseConverter {

	public static Upload getEntityFromRequest(UploadPojo request) {
		if (request == null) {
			return null;
		}
		Upload response = new Upload();
		response.setUrl(request.getUrl());
		response.setType(request.getTag());
		setRequest(response, request);
		return response;
	}

	public static void updateData(UploadPojo request, Upload entity) {
		if (request != null && entity != null) {
			if (!StringUtils.isEmpty(request.getUrl())) {
				entity.setUrl(request.getUrl());
			}
			if (!StringUtils.isEmpty(request.getTag())) {
				entity.setType(request.getTag());
			}
		}

	}

	public static UploadResponse getResponseFromEntity(Upload request) {
		if (request == null) {
			return null;
		}
		UploadResponse response = new UploadResponse();
		response.setId(request.getId());
		response.setUrl(request.getUrl());
		response.setTag(request.getType());
		setResponse(request, response);
		return response;
	}

	public static List<UploadResponse> getResponseListFromEntityList(List<Upload> requestList) {
		if (CollectionUtils.isEmpty(requestList)) {
			return null;
		}
		List<UploadResponse> responseList = new ArrayList<>();
		responseList = requestList.stream().map(request -> getResponseFromEntity(request)).collect(Collectors.toList());
		return responseList;
	}

	public static Upload getEntityFromUploadMap(Map<String, String> map) {
		if (CollectionUtils.sizeIsEmpty(map)) {
			return null;
		}
		Upload response = new Upload();
		response.setUrl(map.get("url"));
		response.setType(map.get("tag"));
		return response;
	}

}
