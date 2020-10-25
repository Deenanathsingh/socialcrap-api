package com.socialcrap.api.common.service;

import java.util.List;

import com.socialcrap.api.exception.SocialCrapException;
import com.socialcrap.api.model.request.dto.NotificationRequest;
import com.socialcrap.api.model.response.dto.NotificationResponse;
import com.socialcrap.api.model.response.dto.PaginationResponse;

public interface NotificationService extends AbstractService<NotificationResponse, NotificationRequest> {

	public void deactivateAllByUserId(Long userId);

	public List<NotificationResponse> getAllByUserId(Long userId);

	public List<NotificationResponse> getAllActiveByUserId(Long userId);

	public PaginationResponse<List<NotificationResponse>> getAllByAdmin(String token, Integer limit, Integer offset)
			throws SocialCrapException;

	public Long getTotalCount();

	public Long getTotalCountOfAllByAdminToken(String token);

	public Boolean sendNotification(NotificationRequest request, String token)
			throws SocialCrapException;
}
