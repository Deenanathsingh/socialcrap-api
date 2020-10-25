package com.socialcrap.api.common.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.socialcrap.api.model.pojo.UploadPojo;
import com.socialcrap.api.model.response.dto.UploadResponse;

public interface UploadService extends AbstractService<UploadResponse, UploadPojo> {

	public List<UploadResponse> getByUserId(Long userId);

	public List<String> getPhotosByUserId(Long userId);

	public List<String> getVideosByUserId(Long userId);

	public Boolean uploadProfilePic(Long userId, MultipartFile profile);

	public Boolean uploadCoverPhoto(Long userId, MultipartFile profile);

}
