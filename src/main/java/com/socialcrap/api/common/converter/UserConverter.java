package com.socialcrap.api.common.converter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.util.StringUtils;

import com.socialcrap.api.model.entity.User;
import com.socialcrap.api.model.request.dto.UserRequest;
import com.socialcrap.api.model.response.dto.AboutResponse;
import com.socialcrap.api.model.response.dto.AddressResponse;
import com.socialcrap.api.model.response.dto.RecoveryResponse;
import com.socialcrap.api.model.response.dto.UserLoginResponse;
import com.socialcrap.api.model.response.dto.UserResponse;
import com.socialcrap.api.model.wrap.pojo.UserWrap;
import com.socialcrap.api.util.JsonUtil;

public class UserConverter extends BaseConverter {

	public static User getEntityFromRequest(UserRequest request) {
		if (request == null) {
			return null;
		}
		User response = new User();
		response.setFirstName(request.getFirstName());
		response.setLastName(request.getLastName());
		response.setMobileNumber(request.getMobileNumber());
		response.setEmail(request.getEmail());
		if (!StringUtils.isEmpty(request.getAbout())) {
			response.setAbout(JsonUtil.getJson(request.getAbout()));
		}
		setRequest(response, request);
		return response;
	}

	public static void updateData(UserRequest request, User entity) {
		if (request != null && entity != null) {
			if (!StringUtils.isEmpty(request.getFirstName())) {
				entity.setFirstName(request.getFirstName());
			}
			if (!StringUtils.isEmpty(request.getLastName())) {
				entity.setLastName(request.getLastName());
			}
			if (!StringUtils.isEmpty(request.getEmail())) {
				entity.setEmail(request.getEmail());
			}
			if (!StringUtils.isEmpty(request.getMobileNumber())) {
				entity.setMobileNumber(request.getMobileNumber());
			}
			if (!StringUtils.isEmpty(request.getProfilePic())) {
				entity.setProfilePhotoLink(request.getProfilePic());
			}
			if (!StringUtils.isEmpty(request.getCoverPhoto())) {
				entity.setCoverPhotoLink(request.getCoverPhoto());
			}
		}
	}

	public static UserResponse getResponseFromEntity(User request) {
		if (request == null) {
			return null;
		}
		UserResponse response = new UserResponse();
		response.setId(request.getId());
		response.setFirstName(request.getFirstName());
		response.setLastName(request.getLastName());
		response.setMobileNumber(request.getMobileNumber());
		response.setEmail(request.getEmail());
		if (!StringUtils.isEmpty(request.getAbout())) {
			response.setAbout(JsonUtil.convertToPojo(request.getAbout(), AboutResponse.class));
		}
		if (!StringUtils.isEmpty(request.getAddress())) {
			response.setAddress(JsonUtil.convertToPojo(request.getAddress(), AddressResponse.class));
		}
		if (!StringUtils.isEmpty(request.getRecoveryDetails())) {
			response.setRecoveryDetails(JsonUtil.convertToPojo(request.getRecoveryDetails(), RecoveryResponse.class));
		}
		response.setProfilePic(request.getProfilePhotoLink());
		response.setCoverPhoto(request.getCoverPhotoLink());
		if (request.isBlocked()) {
			response.setStatus("BLOCKED");
		} else {
			if (request.isActive()) {
				response.setStatus("ACTIVE");
			} else {
				response.setStatus("INACTIVE");
			}
		}
		response.setBlocked(request.isBlocked());
		setResponse(request, response);
		return response;
	}

	public static List<UserResponse> getResponseListFromEntityList(List<User> requestList) {
		if (CollectionUtils.isEmpty(requestList)) {
			return new ArrayList<>();
		}
		List<UserResponse> responseList = new ArrayList<>();
		responseList = requestList.stream().map(request -> getResponseFromEntity(request)).collect(Collectors.toList());
		return responseList;
	}

	public static UserWrap getWrappedEntity(User request) {
		if (request == null) {
			return null;
		}
		UserWrap response = new UserWrap();
		response.setUserId(request.getId());
		response.setName(request.getFirstName() + " " + request.getLastName());
		response.setProfilePic(request.getProfilePhotoLink());
		return response;
	}

	public static List<UserWrap> getWrappedEntityList(List<User> requestList) {
		if (CollectionUtils.isEmpty(requestList)) {
			return new ArrayList<>();
		}
		List<UserWrap> responseList = new ArrayList<>();
		responseList = requestList.stream().map(request -> getWrappedEntity(request)).collect(Collectors.toList());
		return responseList;
	}

	public static UserLoginResponse getUserLoginResponseFromEntity(User request) {
		if (request == null) {
			return null;
		}
		UserLoginResponse response = new UserLoginResponse();
		response.setId(request.getId());
		response.setFirstName(request.getFirstName());
		response.setLastName(request.getLastName());
		response.setMobileNumber(request.getMobileNumber());
		response.setEmail(request.getEmail());
		response.setProfilePic(request.getProfilePhotoLink());
		response.setCoverPhoto(request.getCoverPhotoLink());
		setResponse(request, response);
		return response;
	}

}
