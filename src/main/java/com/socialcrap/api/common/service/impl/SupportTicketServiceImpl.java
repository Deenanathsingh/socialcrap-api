package com.socialcrap.api.common.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.socialcrap.api.common.converter.StatusHistoryConverter;
import com.socialcrap.api.common.converter.SupportTicketConverter;
import com.socialcrap.api.common.converter.UserConverter;
import com.socialcrap.api.common.enums.HistoryStatus;
import com.socialcrap.api.common.enums.SocialCrapResponseCode;
import com.socialcrap.api.common.enums.Sorting;
import com.socialcrap.api.common.service.LoginTokenService;
import com.socialcrap.api.common.service.StatusHistoryService;
import com.socialcrap.api.common.service.SupportTicketService;
import com.socialcrap.api.exception.SocialCrapException;
import com.socialcrap.api.model.dao.SupportTicketDao;
import com.socialcrap.api.model.dao.UserDao;
import com.socialcrap.api.model.entity.StatusHistory;
import com.socialcrap.api.model.entity.SupportTicket;
import com.socialcrap.api.model.entity.User;
import com.socialcrap.api.model.request.dto.StatusUpdateRequest;
import com.socialcrap.api.model.request.dto.SupportTicketRequest;
import com.socialcrap.api.model.response.dto.PaginationResponse;
import com.socialcrap.api.model.response.dto.StatusHistoryResponse;
import com.socialcrap.api.model.response.dto.SupportTicketResponse;
import com.socialcrap.api.util.PaginationUtil;

@Service
public class SupportTicketServiceImpl implements SupportTicketService {

	@Autowired
	private SupportTicketDao supportTicketDao;

	@Autowired
	private StatusHistoryService statusHistoryService;

	@Autowired
	private UserDao userDao;

	@Autowired
	private LoginTokenService loginTokenService;

	@Override
	public List<SupportTicketResponse> getAll() {
		return getResponseList(supportTicketDao.getAllSorted(Sorting.DESC, StatusHistory.Table.CREATED));
	}

	@Override
	public SupportTicketResponse getById(Long id) {
		return getResponse(supportTicketDao.getTById(id));
	}

	@Override
	public Long add(SupportTicketRequest request) throws SocialCrapException {
		if (request == null) {
			throw new SocialCrapException(SocialCrapResponseCode.EMPTY_REQUEST.getMessage(),
					SocialCrapResponseCode.EMPTY_REQUEST.getCode(), HttpStatus.BAD_REQUEST);
		}
		SupportTicket entity = SupportTicketConverter.getEntityFromRequest(request);
		if (entity == null) {
			return 0l;
		}
		User sentBy = userDao.getTById(request.getSentBy());
		if (sentBy == null) {
			throw new SocialCrapException(SocialCrapResponseCode.USER_ID_NOT_FOUND.getMessage(),
					SocialCrapResponseCode.USER_ID_NOT_FOUND.getCode(), HttpStatus.NOT_FOUND);
		}
		entity.setSentBy(sentBy);
		supportTicketDao.saveT(entity);
		saveHistory(false, entity, null);
		return entity.getId();
	}

	@Override
	public Boolean update(SupportTicketRequest request, Long id) throws SocialCrapException {
		return null;
	}

	@Override
	public Boolean delete(Long id) throws SocialCrapException {
		return null;
	}

	@Override
	public void validateId(Long id) throws SocialCrapException {

	}

	@Override
	public void activate(Long id) throws SocialCrapException {

	}

	@Override
	public void deactivate(Long id) throws SocialCrapException {

	}

	@Override
	public void addAll(List<SupportTicketRequest> requestList) {

	}

	@Override
	public void updateAll(List<SupportTicketRequest> requestList) {

	}

	private SupportTicketResponse getResponse(SupportTicket request) {
		if (request == null) {
			return null;
		}
		SupportTicketResponse response = SupportTicketConverter.getResponseFromEntity(request);

		List<StatusHistoryResponse> statusHistory = statusHistoryService.getAllByReferTo(getReferenceKey(request));
		if (!CollectionUtils.isEmpty(statusHistory)) {
			response.setCurrentStatus(statusHistory.get(0));
			response.setStatusHistory(statusHistory);
		}
		response.setSentBy(UserConverter.getWrappedEntity(request.getSentBy()));
		return response;
	}

	private List<SupportTicketResponse> getResponseList(List<SupportTicket> requestList) {
		if (CollectionUtils.isEmpty(requestList)) {
			return null;
		}
		List<SupportTicketResponse> responseList = requestList.stream().map(r -> getResponse(r))
				.collect(Collectors.toList());
		return responseList;
	}

	private String getReferenceKey(SupportTicket request) {
		StringBuilder referenceKey = new StringBuilder(request.getClass().getSimpleName().toUpperCase());
		referenceKey.append(request.getId());
		return referenceKey.toString();
	}

	private void saveHistory(boolean isExist, SupportTicket entity, StatusUpdateRequest request) {
		StatusHistory history = null;
		String referenceKey = getReferenceKey(entity);
		if (referenceKey == null) {
			return;
		}
		if (isExist) {
			history = StatusHistoryConverter.getEntityFromRequest(request);
			history.setReferTo(referenceKey);
			history.setUpdatedBy(userDao.getTById(request.getUserId()));
		} else {
			history = StatusHistoryConverter.getEntityFromTicket(entity);
			history.setReferTo(referenceKey);
		}
		if (history != null) {
			statusHistoryService.save(history);
		}
	}

	@Override
	public List<SupportTicketResponse> getBySentById(Long sentById) {
		if (sentById == null) {
			return null;
		}
		if (userDao.isExist(sentById)) {
			List<SupportTicket> entityList = supportTicketDao.getAllBySentBy(sentById);
			return getResponseList(entityList);
		}
		return null;
	}

	@Override
	public void updateStatus(StatusUpdateRequest request) throws SocialCrapException {
		if (request == null) {
			throw new SocialCrapException(SocialCrapResponseCode.EMPTY_REQUEST.getMessage(),
					SocialCrapResponseCode.EMPTY_REQUEST.getCode(), HttpStatus.BAD_REQUEST);
		}
		SupportTicket entity = supportTicketDao.getTById(request.getReferenceId());
		if (entity == null) {
			throw new SocialCrapException(SocialCrapResponseCode.SUPPORT_TICKET_ID_NOT_FOUND.getMessage(),
					SocialCrapResponseCode.SUPPORT_TICKET_ID_NOT_FOUND.getCode(), HttpStatus.NOT_FOUND);
		}
		entity.setCurrentStatus(HistoryStatus.valueOf(request.getStatus()).getStatus());
		saveHistory(true, entity, request);
		supportTicketDao.updateT(entity);
	}

	@Override
	public PaginationResponse<List<SupportTicketResponse>> getAll(String token, Integer limit, Integer offset,
			String status) throws SocialCrapException {
		loginTokenService.validateAdminToken(token);
		status = status.trim();
		List<SupportTicket> entityList = new ArrayList<>();
		if (StringUtils.isEmpty(status)) {
			entityList = supportTicketDao.getAllT(limit, PaginationUtil.getOffset(limit, offset));
		} else {
			entityList = supportTicketDao.getAllByStatus(status, limit, PaginationUtil.getOffset(limit, offset));
		}
		PaginationResponse<List<SupportTicketResponse>> responseList = null;
		if (!CollectionUtils.isEmpty(entityList)) {
			responseList = new PaginationResponse<>(getResponseList(entityList));
			return responseList;
		}
		return new PaginationResponse<>(new ArrayList<>());
	}

	@Override
	public Long getTotalCount() {
		return supportTicketDao.getTotalCount();
	}

}
