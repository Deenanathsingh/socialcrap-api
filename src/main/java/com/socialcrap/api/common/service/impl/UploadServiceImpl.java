package com.socialcrap.api.common.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.socialcrap.api.common.converter.UploadConverter;
import com.socialcrap.api.common.service.FileService;
import com.socialcrap.api.common.service.UploadService;
import com.socialcrap.api.exception.SocialCrapException;
import com.socialcrap.api.model.dao.UploadDao;
import com.socialcrap.api.model.dao.UserDao;
import com.socialcrap.api.model.entity.Upload;
import com.socialcrap.api.model.entity.User;
import com.socialcrap.api.model.pojo.UploadPojo;
import com.socialcrap.api.model.response.dto.UploadResponse;

@Service
public class UploadServiceImpl implements UploadService {

	@Autowired
	private UploadDao uploadDao;

	@Autowired
	private FileService fileService;

	@Autowired
	private UserDao userDao;

	@Override
	public List<UploadResponse> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UploadResponse getById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long add(UploadPojo request) throws SocialCrapException {
		if (request == null) {
			return null;
		}
		Upload entity = UploadConverter.getEntityFromRequest(request);
		if (entity == null) {
			return null;
		}
		uploadDao.saveT(entity);
		return entity.getId();
	}

	@Override
	public Boolean update(UploadPojo request, Long id) throws SocialCrapException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean delete(Long id) throws SocialCrapException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void validateId(Long id) throws SocialCrapException {
		// TODO Auto-generated method stub

	}

	@Override
	public void activate(Long id) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deactivate(Long id) {
		// TODO Auto-generated method stub

	}

	@Override
	public void addAll(List<UploadPojo> requestList) {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateAll(List<UploadPojo> requestList) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<UploadResponse> getByUserId(Long userId) {
		List<Upload> entityList = uploadDao.getAllByUser(userId);
		if (CollectionUtils.isEmpty(entityList)) {
			return null;
		}
		List<UploadResponse> responseList = new ArrayList<>();
		responseList = UploadConverter.getResponseListFromEntityList(entityList);
		return responseList;
	}

	@Override
	public List<String> getPhotosByUserId(Long userId) {
		List<Upload> entityList = uploadDao.getAllPhotosByUser(userId);
		if (CollectionUtils.isEmpty(entityList)) {
			return null;
		}
		List<String> responseList = new ArrayList<>();
		responseList = entityList.stream().map(Upload::getUrl).collect(Collectors.toList());
		return responseList;
	}

	@Override
	public List<String> getVideosByUserId(Long userId) {
		List<Upload> entityList = uploadDao.getAllVideosByUser(userId);
		if (CollectionUtils.isEmpty(entityList)) {
			return null;
		}
		List<String> responseList = new ArrayList<>();
		responseList = entityList.stream().map(Upload::getUrl).collect(Collectors.toList());
		return responseList;
	}

	@Override
	public Boolean uploadProfilePic(Long userId, MultipartFile profile) {
		User user = userDao.getTById(userId);
		if (user == null) {
			return false;
		}
		Map<String, String> profilePic = new HashMap<>();
		profilePic = fileService.uploadFile(profile);
		Upload entity = UploadConverter.getEntityFromUploadMap(profilePic);
		if (entity == null) {
			return false;
		}
		entity.setUploadedBy(user);
		uploadDao.saveT(entity);
		user.setProfilePhotoLink(entity.getUrl());
		userDao.updateT(user);
		return true;
	}

	@Override
	public Boolean uploadCoverPhoto(Long userId, MultipartFile profile) {
		User user = userDao.getTById(userId);
		if (user == null) {
			return false;
		}
		Map<String, String> profilePic = new HashMap<>();
		profilePic = fileService.uploadFile(profile);
		Upload entity = UploadConverter.getEntityFromUploadMap(profilePic);
		if (entity == null) {
			return false;
		}
		entity.setUploadedBy(user);
		uploadDao.saveT(entity);
		user.setCoverPhotoLink(entity.getUrl());
		userDao.updateT(user);
		return true;
	}

}
