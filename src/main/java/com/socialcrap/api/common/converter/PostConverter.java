package com.socialcrap.api.common.converter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.util.StringUtils;

import com.socialcrap.api.model.entity.Post;
import com.socialcrap.api.model.request.dto.PostRequest;
import com.socialcrap.api.model.response.dto.PostResponse;

public class PostConverter extends BaseConverter {

	public static Post getEntityFromRequest(PostRequest request) {
		Post response = new Post();
		response.setTitle(request.getTitle());
		response.setPost(request.getPost());
		setRequest(response, request);
		return response;
	}

	public static void updateData(PostRequest request, Post entity) {
		if (request != null && entity != null) {
			if (!StringUtils.isEmpty(request.getTitle())) {
				entity.setTitle(request.getTitle());
			}
			if (!StringUtils.isEmpty(request.getPost())) {
				entity.setPost(request.getPost());
			}
		}

	}

	public static PostResponse getResponseFromEntity(Post request) {
		PostResponse response = new PostResponse();
		response.setId(request.getId());
		response.setTitle(request.getTitle());
		response.setPost(request.getPost());
		setResponse(request, response);
		return response;
	}

	public static List<PostResponse> getResponseListFromEntityList(List<Post> requestList) {
		List<PostResponse> responseList = new ArrayList<>();
		responseList = requestList.stream().map(request -> getResponseFromEntity(request)).collect(Collectors.toList());
		return responseList;
	}

}
