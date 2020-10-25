package com.socialcrap.api.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.socialcrap.api.common.constants.RestMappingConstants;
import com.socialcrap.api.common.enums.SocialCrapResponseCode;
import com.socialcrap.api.common.service.PostService;
import com.socialcrap.api.exception.SocialCrapException;
import com.socialcrap.api.model.request.dto.PostLikeRequest;
import com.socialcrap.api.model.request.dto.PostRequest;
import com.socialcrap.api.model.response.dto.BaseApiResponse;
import com.socialcrap.api.model.response.dto.PaginationResponse;
import com.socialcrap.api.model.response.dto.PostResponse;
import com.socialcrap.api.util.PaginationUtil;

@CrossOrigin
@RestController
@RequestMapping(value = RestMappingConstants.PostConstants.BASE)
public class PostController {

	@Autowired
	private PostService postService;

	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public PaginationResponse<List<PostResponse>> getAll(@RequestHeader(RestMappingConstants.TOKEN) String token,
			HttpServletRequest request, @RequestParam(required = false, defaultValue = "10") Integer limit,
			@RequestParam(required = false, defaultValue = "1") Integer offset) throws SocialCrapException {
		PaginationResponse<List<PostResponse>> response = postService.getAll(token, limit, offset);
		Long totalCount = postService.getTotalCount();
		PaginationUtil.setPageData(limit, offset, response, totalCount, request);
		return response;
	}

	@GetMapping(path = RestMappingConstants.PostConstants.CUSTOM)
	@ResponseStatus(HttpStatus.OK)
	public BaseApiResponse<List<PostResponse>> getAllCustom(@RequestParam Long userId) {
		List<PostResponse> responseList = postService.getCustomPostForUser(userId);
		if (CollectionUtils.isEmpty(responseList)) {
			return new BaseApiResponse<>(false, SocialCrapResponseCode.POSTS_NOT_FOUND.getCode(), new ArrayList<>(),
					SocialCrapResponseCode.POSTS_NOT_FOUND.getMessage());
		}
		return new BaseApiResponse<>(responseList);
	}

	@GetMapping(path = RestMappingConstants.ID_PARAM)
	@ResponseStatus(HttpStatus.OK)
	public BaseApiResponse<PostResponse> getById(@PathVariable(RestMappingConstants.ID) Long id) {
		PostResponse response = postService.getById(id);
		return new BaseApiResponse<>(response);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.OK)
	public BaseApiResponse<Long> add(@Valid @RequestBody(required = true) PostRequest request)
			throws SocialCrapException {
		Long postId = postService.add(request);
		return new BaseApiResponse<>(postId);
	}

	@PatchMapping(path = RestMappingConstants.ID_PARAM)
	@ResponseStatus(HttpStatus.OK)
	public BaseApiResponse<Boolean> update(@Valid @RequestBody(required = true) PostRequest request,
			@PathVariable(RestMappingConstants.ID) Long id) throws SocialCrapException {
		Boolean response = postService.update(request, id);
		return new BaseApiResponse<>(response);
	}

	@DeleteMapping(path = RestMappingConstants.ID_PARAM)
	@ResponseStatus(HttpStatus.OK)
	public BaseApiResponse<Boolean> delete(@PathVariable(RestMappingConstants.ID) Long id) throws SocialCrapException {
		Boolean response = postService.delete(id);
		return new BaseApiResponse<>(response);
	}

	@PostMapping(path = RestMappingConstants.PostConstants.LIKE)
	@ResponseStatus(HttpStatus.OK)
	public BaseApiResponse<Boolean> like(@Valid @RequestBody(required = true) PostLikeRequest request)
			throws SocialCrapException {
		if (request == null) {
			return new BaseApiResponse<>(false);
		}
		Boolean response = postService.like(request);
		return new BaseApiResponse<>(response);
	}

	@PostMapping(path = RestMappingConstants.PostConstants.DIS_LIKE)
	@ResponseStatus(HttpStatus.OK)
	public BaseApiResponse<Boolean> disLike(@Valid @RequestBody(required = true) PostLikeRequest request) {
		if (request == null) {
			return new BaseApiResponse<>(false);
		}
		Boolean response = postService.disLike(request);
		return new BaseApiResponse<>(response);

	}

	@PatchMapping(path = RestMappingConstants.ACTIVATE_AND_DEACTIVATE + RestMappingConstants.ID_PARAM)
	@ResponseStatus(HttpStatus.OK)
	public BaseApiResponse<Boolean> activate(@PathVariable(RestMappingConstants.ID) Long id)
			throws SocialCrapException {
		Boolean response = postService.activateAndDeactivate(id);
		return new BaseApiResponse<>(response);
	}

	@PatchMapping(path = RestMappingConstants.DE_ACTIVATE + RestMappingConstants.ID_PARAM)
	@ResponseStatus(HttpStatus.OK)
	public BaseApiResponse<Boolean> deActivate(@PathVariable(RestMappingConstants.ID) Long id)
			throws SocialCrapException {
		postService.deactivate(id);
		return new BaseApiResponse<>(true);
	}

}
