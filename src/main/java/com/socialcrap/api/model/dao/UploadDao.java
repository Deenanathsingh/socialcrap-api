package com.socialcrap.api.model.dao;

import java.util.List;

import com.socialcrap.api.model.entity.Upload;

public interface UploadDao extends AbstractDao<Upload> {

	public List<Upload> getAllByUser(Long userId);

	public List<Upload> getAllPhotosByUser(Long userId);

	public List<Upload> getAllVideosByUser(Long userId);

}
