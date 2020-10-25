package com.socialcrap.api.model.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.socialcrap.api.common.enums.UploadTag;
import com.socialcrap.api.model.dao.UploadDao;
import com.socialcrap.api.model.entity.Upload;

@Repository
public class UploadDaoImpl extends AbstractDaoImpl<Upload> implements UploadDao {

	public UploadDaoImpl() {
		super(Upload.class);
	}

	@Override
	public List<Upload> getAllByUser(Long userId) {
		String hql = String.format(FROM_ENTITY + " where %s=%s", Upload.Table.UPLOADED_BY, userId);
		return getAllT(hql);
	}

	@Override
	public List<Upload> getAllPhotosByUser(Long userId) {
		String hql = String.format(FROM_ENTITY + " where %s=%s and %s='%s'", Upload.Table.UPLOADED_BY, userId,
				Upload.Table.TYPE, UploadTag.PHOTO.getLabel());
		return getAllT(hql);
	}

	@Override
	public List<Upload> getAllVideosByUser(Long userId) {
		String hql = String.format(FROM_ENTITY + " where %s=%s and %s='%s'", Upload.Table.UPLOADED_BY, userId,
				Upload.Table.TYPE, UploadTag.VIDEO.getLabel());
		return getAllT(hql);
	}

}
