package com.socialcrap.api.common.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.socialcrap.api.common.converter.NotificationConverter;
import com.socialcrap.api.common.converter.UserConverter;
import com.socialcrap.api.common.enums.SocialCrapResponseCode;
import com.socialcrap.api.common.service.LoginTokenService;
import com.socialcrap.api.common.service.NotificationService;
import com.socialcrap.api.exception.SocialCrapException;
import com.socialcrap.api.model.dao.NotificationDao;
import com.socialcrap.api.model.dao.UserDao;
import com.socialcrap.api.model.entity.Notification;
import com.socialcrap.api.model.entity.User;
import com.socialcrap.api.model.request.dto.NotificationRequest;
import com.socialcrap.api.model.response.dto.NotificationResponse;
import com.socialcrap.api.model.response.dto.PaginationResponse;
import com.socialcrap.api.util.PaginationUtil;

@Service
public class NotificationServiceImpl implements NotificationService {

	@Autowired
	private NotificationDao notificationDao;
	@Autowired
	private UserDao userDao;
	@Autowired
	private LoginTokenService loginTokenService;

	@Override
	public List<NotificationResponse> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NotificationResponse getById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long add(NotificationRequest request) throws SocialCrapException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean update(NotificationRequest request, Long id) throws SocialCrapException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean delete(Long id) throws SocialCrapException {
		validateId(id);
		notificationDao.deleteT(id);
		return true;
	}

	@Override
	public void validateId(Long id) throws SocialCrapException {
		if (!notificationDao.isExist(id)) {
			String message = String.format(SocialCrapResponseCode.NOTIFICATION_ID_NOT_FOUND.getMessage(), id);
			throw new SocialCrapException(message, SocialCrapResponseCode.NOTIFICATION_ID_NOT_FOUND.getCode(),
					HttpStatus.NOT_FOUND);
		}
	}

	@Override
	public void activate(Long id) throws SocialCrapException {
		validateId(id);
		notificationDao.activateAndDeActivate(id, true);
	}

	@Override
	public void deactivate(Long id) throws SocialCrapException {
		validateId(id);
		notificationDao.activateAndDeActivate(id, false);
	}

	@Override
	public void addAll(List<NotificationRequest> requestList) {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateAll(List<NotificationRequest> requestList) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deactivateAllByUserId(Long userId) {
		if (userId != null) {
			User user = userDao.getActiveTById(userId);
			List<Notification> notifications = user.getNotifications();
			if (!CollectionUtils.isEmpty(notifications)) {
				notifications.forEach(n -> {
					try {
						deactivate(n.getId());
					} catch (SocialCrapException e) {
						e.printStackTrace();
					}
				});
			}
		}
	}

	@Override
	public List<NotificationResponse> getAllByUserId(Long userId) {
		return NotificationConverter.getResponseListFromEntityList(notificationDao.getAllByUserId(userId));
	}

	@Override
	public List<NotificationResponse> getAllActiveByUserId(Long userId) {
		return NotificationConverter.getResponseListFromEntityList(notificationDao.getAllActiveByUserId(userId));
	}

	@Override
	public PaginationResponse<List<NotificationResponse>> getAllByAdmin(String token, Integer limit, Integer offset)
			throws SocialCrapException {

		Long adminId = loginTokenService.validateAdminToken(token);

		List<Notification> entityList = notificationDao.getAllByAdminId(adminId, limit,
				PaginationUtil.getOffset(limit, offset));
		PaginationResponse<List<NotificationResponse>> responseList = null;
		if (!CollectionUtils.isEmpty(entityList)) {
			responseList = new PaginationResponse<>(getResponseList(entityList));
			return responseList;
		}
		return new PaginationResponse<>(new ArrayList<>());
	}

	@Override
	public Long getTotalCount() {
		return notificationDao.getTotalCount();
	}

	@Override
	public Long getTotalCountOfAllByAdminToken(String token) {
		Long adminId = 0l;
		try {
			adminId = loginTokenService.validateAdminToken(token);
		} catch (SocialCrapException e) {
		}
		return notificationDao.getCountOfAllByAdminId(adminId);
	}

	@Override
	public Boolean sendNotification(NotificationRequest request, String token) throws SocialCrapException {

		Long adminId = loginTokenService.validateAdminToken(token);

		if (CollectionUtils.isEmpty(request.getSentTo()) || request == null) {
			throw new SocialCrapException(SocialCrapResponseCode.EMPTY_REQUEST.getMessage(),
					SocialCrapResponseCode.EMPTY_REQUEST.getCode(), HttpStatus.BAD_REQUEST);
		}
		request.getSentTo().forEach(id -> {
			if (userDao.isExist(id)) {
				Notification entity = NotificationConverter.getEntityFromRequest(request.getNotification(),
						userDao.getTById(id));
				if (entity != null) {
					entity.setSentBy(userDao.getTById(adminId));
					notificationDao.saveT(entity);
				}
			}
		});
		return true;
	}

	private NotificationResponse getResponse(Notification request) {
		if (request == null) {
			return null;
		}
		NotificationResponse response = NotificationConverter.getResponseFromEntity(request);
		if (request.getSentBy() != null) {
			response.setSentBy(UserConverter.getWrappedEntity(request.getSentBy()));
		}
		if (request.getSentTo() != null) {
			response.setSentTo(UserConverter.getWrappedEntity(request.getSentTo()));
		}
		return response;
	}

	private List<NotificationResponse> getResponseList(List<Notification> requestList) {
		if (CollectionUtils.isEmpty(requestList)) {
			return null;
		}
		List<NotificationResponse> responseList = requestList.stream().map(r -> getResponse(r))
				.collect(Collectors.toList());
		return responseList;
	}

}
