package com.socialcrap.api.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.socialcrap.api.common.constants.RestMappingConstants;
import com.socialcrap.api.common.service.FileService;
import com.socialcrap.api.model.response.dto.BaseApiResponse;

@CrossOrigin
@RestController
@RequestMapping(value = RestMappingConstants.FileConstants.BASE)
public class FileController {

	@Autowired
	private FileService fileService;

	@PostMapping(path = RestMappingConstants.FileConstants.UPLOAD)
	@ResponseStatus(HttpStatus.OK)
	public BaseApiResponse<Map<String, String>> upload(@RequestParam MultipartFile file) {
		Map<String, String> fileData = fileService.uploadFile(file);
		return new BaseApiResponse<>(fileData);
	}

	@PostMapping(path = RestMappingConstants.FileConstants.ATTACH)
	@ResponseStatus(HttpStatus.OK)
	public BaseApiResponse<List<String>> attach(@RequestParam MultipartFile[] file) {
		List<String> fileData = fileService.attachFiles(file);
		if (CollectionUtils.isEmpty(fileData)) {
			return new BaseApiResponse<>(new ArrayList<>());
		}
		return new BaseApiResponse<>(fileData);
	}

}
