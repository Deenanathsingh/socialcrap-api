package com.socialcrap.api.controller;

import java.util.List;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.socialcrap.api.common.constants.RestMappingConstants;
import com.socialcrap.api.common.service.CommentService;
import com.socialcrap.api.exception.SocialCrapException;
import com.socialcrap.api.model.request.dto.CommentLikeRequest;
import com.socialcrap.api.model.request.dto.CommentRequest;
import com.socialcrap.api.model.response.dto.BaseApiResponse;
import com.socialcrap.api.model.response.dto.CommentResponse;

@CrossOrigin
@RestController
@RequestMapping(value = RestMappingConstants.CommentConstants.BASE)
public class CommentController {

	@Autowired
	private CommentService commentService;

	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public BaseApiResponse<List<CommentResponse>> getAll() {
		List<CommentResponse> responseList = commentService.getAll();
		if (!CollectionUtils.isEmpty(responseList)) {
			return new BaseApiResponse<>(responseList);
		}
		return new BaseApiResponse<>(null);
	}

	@GetMapping(path = RestMappingConstants.ID_PARAM)
	@ResponseStatus(HttpStatus.OK)
	public BaseApiResponse<CommentResponse> getById(@PathVariable(RestMappingConstants.ID) Long id) {
		CommentResponse response = commentService.getById(id);
		return new BaseApiResponse<>(response);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.OK)
	public BaseApiResponse<Long> add(@Valid @RequestBody(required = true) CommentRequest request)
			throws SocialCrapException {
		if (request == null) {
			return new BaseApiResponse<>(0L);
		}
		Long response = commentService.add(request);
		return new BaseApiResponse<>(response);
	}

	@PatchMapping(path = RestMappingConstants.ID_PARAM)
	@ResponseStatus(HttpStatus.OK)
	public BaseApiResponse<Boolean> update(CommentRequest request, Long id) throws SocialCrapException {
		if (request == null) {
			return new BaseApiResponse<>(false);
		}
		Boolean response = commentService.update(request, id);
		return new BaseApiResponse<>(response);
	}

	@DeleteMapping(path = RestMappingConstants.ID_PARAM)
	@ResponseStatus(HttpStatus.OK)
	public BaseApiResponse<Boolean> delete(Long id) throws SocialCrapException {
		Boolean response = commentService.delete(id);
		return new BaseApiResponse<>(response);
	}

	@PostMapping(path = RestMappingConstants.CommentConstants.LIKE)
	@ResponseStatus(HttpStatus.OK)
	public BaseApiResponse<Boolean> like(@Valid @RequestBody(required = true) CommentLikeRequest request) {
		if (request == null) {
			return new BaseApiResponse<>(false);
		}
		Boolean response = commentService.like(request);
		return new BaseApiResponse<>(response);
	}

	@PostMapping(path = RestMappingConstants.CommentConstants.DIS_LIKE)
	@ResponseStatus(HttpStatus.OK)
	public BaseApiResponse<Boolean> disLike(@Valid @RequestBody(required = true) CommentLikeRequest request) {
		if (request == null) {
			return new BaseApiResponse<>(false);
		}
		Boolean response = commentService.disLike(request);
		return new BaseApiResponse<>(response);

	}

}
