package com.socialcrap.api.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.socialcrap.api.common.constants.RestMappingConstants;
import com.socialcrap.api.common.enums.SocialCrapResponseCode;
import com.socialcrap.api.common.service.UploadService;
import com.socialcrap.api.exception.SocialCrapException;
import com.socialcrap.api.model.response.dto.BaseApiResponse;

@CrossOrigin
@RestController
@RequestMapping(value = RestMappingConstants.Upload.BASE)
public class UploadController {

	@Autowired
	private UploadService uploadService;

	@GetMapping(path = RestMappingConstants.Upload.PHOTO + RestMappingConstants.USER_ID_PARAM)
	@ResponseStatus(HttpStatus.OK)
	public BaseApiResponse<List<String>> getPhotosByUserId(@PathVariable(RestMappingConstants.USER_ID) Long userId)
			throws SocialCrapException {
		List<String> responseList = uploadService.getPhotosByUserId(userId);
		if (CollectionUtils.isEmpty(responseList)) {
			return new BaseApiResponse<>(false, SocialCrapResponseCode.UPLOAD_NOT_FOUND.getCode(), new ArrayList<>(),
					SocialCrapResponseCode.UPLOAD_NOT_FOUND.getMessage());
		}
		return new BaseApiResponse<>(responseList);
	}

	@GetMapping(path = RestMappingConstants.Upload.VIDEO + RestMappingConstants.USER_ID_PARAM)
	@ResponseStatus(HttpStatus.OK)
	public BaseApiResponse<List<String>> getVideosByUserId(@PathVariable(RestMappingConstants.USER_ID) Long userId)
			throws SocialCrapException {
		List<String> responseList = uploadService.getVideosByUserId(userId);
		if (CollectionUtils.isEmpty(responseList)) {
			return new BaseApiResponse<>(false, SocialCrapResponseCode.UPLOAD_NOT_FOUND.getCode(), new ArrayList<>(),
					SocialCrapResponseCode.UPLOAD_NOT_FOUND.getMessage());
		}
		return new BaseApiResponse<>(responseList);
	}

	@PatchMapping(path = RestMappingConstants.Upload.UPDATE_PROFILE_PIC + RestMappingConstants.USER_ID_PARAM)
	@ResponseStatus(HttpStatus.OK)
	public BaseApiResponse<Boolean> updateProfilePic(@PathVariable(RestMappingConstants.USER_ID) Long userId,
			@RequestParam MultipartFile file) throws SocialCrapException {
		Boolean response = uploadService.uploadProfilePic(userId, file);
		return new BaseApiResponse<>(response);
	}

	@PatchMapping(path = RestMappingConstants.Upload.UPDATE_COVER_PHOTO + RestMappingConstants.USER_ID_PARAM)
	@ResponseStatus(HttpStatus.OK)
	public BaseApiResponse<Boolean> updateCoverPhoto(@PathVariable(RestMappingConstants.USER_ID) Long userId,
			@RequestParam MultipartFile file) throws SocialCrapException {
		Boolean response = uploadService.uploadCoverPhoto(userId, file);
		return new BaseApiResponse<>(response);
	}

}
