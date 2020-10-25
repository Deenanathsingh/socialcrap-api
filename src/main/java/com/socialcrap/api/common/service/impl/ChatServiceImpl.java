package com.socialcrap.api.common.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.socialcrap.api.common.converter.ChatConverter;
import com.socialcrap.api.common.converter.UserConverter;
import com.socialcrap.api.common.enums.SocialCrapResponseCode;
import com.socialcrap.api.common.service.ChatService;
import com.socialcrap.api.exception.SocialCrapException;
import com.socialcrap.api.model.dao.ChatDao;
import com.socialcrap.api.model.dao.UserDao;
import com.socialcrap.api.model.entity.ChatEntity;
import com.socialcrap.api.model.entity.User;
import com.socialcrap.api.model.request.dto.ChatRequest;
import com.socialcrap.api.model.response.dto.ChatResponse;

@Service
public class ChatServiceImpl implements ChatService {

	@Autowired
	private ChatDao chatDao;
	@Autowired
	private UserDao userDao;

	@Override
	public List<ChatResponse> getAll() {
		return null;
	}

	@Override
	public ChatResponse getById(Long id) {
		return null;
	}

	@Override
	public Long add(ChatRequest request) throws SocialCrapException {
		if (request == null) {
			throw new SocialCrapException(SocialCrapResponseCode.EMPTY_REQUEST.getMessage(),
					SocialCrapResponseCode.EMPTY_REQUEST.getCode(), HttpStatus.BAD_REQUEST);
		}
		ChatEntity entity = ChatConverter.getEntityFromRequest(request);
		if (entity == null) {
			throw new SocialCrapException(SocialCrapResponseCode.EMPTY_REQUEST.getMessage(),
					SocialCrapResponseCode.EMPTY_REQUEST.getCode(), HttpStatus.BAD_REQUEST);
		}
		User sentBy = userDao.getTById(request.getSentBy());
		User sentTo = userDao.getTById(request.getSentTo());
		entity.setSentBy(sentBy);
		entity.setSentTo(sentTo);
		chatDao.saveT(entity);
		return entity.getId();
	}

	@Override
	public Boolean update(ChatRequest request, Long id) throws SocialCrapException {
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
	public void addAll(List<ChatRequest> requestList) {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateAll(List<ChatRequest> requestList) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<ChatResponse> getBySentByAndSentTo(Long sentTo, Long sentBy) {
		if (sentTo == null) {
			return null;
		}
		List<ChatEntity> entityList = new ArrayList<>();
		List<ChatEntity> listByActualRequest = chatDao.getBySentToAndSentBy(sentTo, sentBy);
		List<ChatEntity> listByReversedRequest = chatDao.getBySentToAndSentBy(sentBy, sentTo);
		if (!CollectionUtils.isEmpty(listByActualRequest)) {
			entityList.addAll(listByActualRequest);
		}
		if (!CollectionUtils.isEmpty(listByReversedRequest)) {
			entityList.addAll(listByReversedRequest);
		}
		Collections.sort(entityList, (e1, e2) -> e1.getCreated().compareTo(e2.getCreated()));
		return getResponseList(entityList);
	}

	private ChatResponse getResponse(ChatEntity request) {
		if (request == null) {
			return null;
		}
		ChatResponse response = ChatConverter.getResponseFromEntity(request);
		response.setSentBy(UserConverter.getWrappedEntity(request.getSentBy()));
		response.setSentTo(UserConverter.getWrappedEntity(request.getSentTo()));
		return response;
	}

	private List<ChatResponse> getResponseList(List<ChatEntity> requestList) {
		if (CollectionUtils.isEmpty(requestList)) {
			return null;
		}
		List<ChatResponse> responseList = new ArrayList<>();
		responseList = requestList.stream().map(r -> getResponse(r)).collect(Collectors.toList());
		return responseList;
	}

}
