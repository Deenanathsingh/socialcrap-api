package com.socialcrap.api.common.converter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.util.StringUtils;

import com.socialcrap.api.model.entity.Role;
import com.socialcrap.api.model.request.dto.RoleRequest;
import com.socialcrap.api.model.response.dto.RoleResponse;

public class RoleConverter extends BaseConverter {

	public static Role getEntityFromRequest(RoleRequest request) {
		if (request == null) {
			return null;
		}
		Role response = new Role();
		response.setRole(request.getRole().toUpperCase());
		setRequest(response, request);
		return response;
	}

	public static void updateData(RoleRequest request, Role entity) {
		if (request != null && entity != null) {
			if (!StringUtils.isEmpty(request.getRole())) {
				entity.setRole(request.getRole());
			}
		}
	}

	public static RoleResponse getResponseFromEntity(Role entity) {
		if (entity == null) {
			return null;
		}
		RoleResponse response = new RoleResponse();
		response.setRole(entity.getRole());
		setResponse(entity, response);
		return response;
	}

	public static List<RoleResponse> getResponseListFromEntityList(List<Role> requestList) {
		if (CollectionUtils.isEmpty(requestList)) {
			return new ArrayList<>();
		}
		List<RoleResponse> responseList = new ArrayList<>();
		responseList = requestList.stream().map(r -> getResponseFromEntity(r)).collect(Collectors.toList());
		return responseList;
	}
}
