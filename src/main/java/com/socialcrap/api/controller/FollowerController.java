package com.socialcrap.api.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.socialcrap.api.common.constants.RestMappingConstants;
import com.socialcrap.api.common.service.FollowerService;
import com.socialcrap.api.exception.SocialCrapException;
import com.socialcrap.api.model.request.dto.FollowerRequest;
import com.socialcrap.api.model.response.dto.BaseApiResponse;

@RestController
@RequestMapping(value = RestMappingConstants.Follower.BASE)
public class FollowerController {

	@Autowired
	private FollowerService followerService;

	@PostMapping
	public BaseApiResponse<Boolean> add(@Valid @RequestBody FollowerRequest request) throws SocialCrapException {
		Long followerId = followerService.add(request);
		if (followerId == null) {
			return new BaseApiResponse<Boolean>(false);
		}
		return new BaseApiResponse<Boolean>(true);
	}

}
