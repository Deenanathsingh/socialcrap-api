package com.socialcrap.api.common.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.socialcrap.api.common.constants.EntityDetails;
import com.socialcrap.api.common.converter.EmailConverter;
import com.socialcrap.api.common.converter.UserConverter;
import com.socialcrap.api.common.enums.SocialCrapResponseCode;
import com.socialcrap.api.common.enums.Sorting;
import com.socialcrap.api.common.service.EmailService;
import com.socialcrap.api.common.service.LoginTokenService;
import com.socialcrap.api.common.service.UserService;
import com.socialcrap.api.exception.SocialCrapException;
import com.socialcrap.api.model.dao.EmailDao;
import com.socialcrap.api.model.dao.UserDao;
import com.socialcrap.api.model.entity.Email;
import com.socialcrap.api.model.request.dto.EmailRequest;
import com.socialcrap.api.model.response.dto.EmailResponse;
import com.socialcrap.api.model.response.dto.PaginationResponse;
import com.socialcrap.api.util.EmailUtil;
import com.socialcrap.api.util.PaginationUtil;

@Service
public class EmailServiceImpl implements EmailService {

	@Autowired
	private EmailDao emailDao;
	@Autowired
	private UserDao userDao;
	@Autowired
	private UserService userService;
	@Autowired
	private LoginTokenService loginTokenService;

	@Override
	public List<EmailResponse> getAll() {
		return getResponseList(emailDao.getAllSorted(Sorting.DESC, EntityDetails.Email.CREATED_DATE));
	}

	@Override
	public EmailResponse getById(Long id) {
		return getResponse(emailDao.getTById(id));
	}

	@Override
	public Long add(EmailRequest request) throws SocialCrapException {
		if (StringUtils.isEmpty(request)) {
			throw new SocialCrapException(SocialCrapResponseCode.EMPTY_REQUEST.getMessage(),
					SocialCrapResponseCode.EMPTY_REQUEST.getCode(), HttpStatus.BAD_REQUEST);
		}
		userService.validateId(request.getUserId());
		Email entity = EmailConverter.getEntityFromRequest(request);
		if (StringUtils.isEmpty(entity)) {
			throw new SocialCrapException(SocialCrapResponseCode.EMPTY_REQUEST.getMessage(),
					SocialCrapResponseCode.EMPTY_REQUEST.getCode(), HttpStatus.BAD_REQUEST);
		}
		emailDao.saveT(entity);
		return entity.getId();
	}

	@Override
	public Boolean sendEmail(String token, EmailRequest request) throws SocialCrapException {

		loginTokenService.validateAdminToken(token);

		if (StringUtils.isEmpty(request)) {
			throw new SocialCrapException(SocialCrapResponseCode.EMPTY_REQUEST.getMessage(),
					SocialCrapResponseCode.EMPTY_REQUEST.getCode(), HttpStatus.BAD_REQUEST);
		}
		userService.validateId(request.getUserId());
		Boolean sent = EmailUtil.sendEmail(request);
		if (!sent) {
			throw new SocialCrapException(SocialCrapResponseCode.EMAIL_SENT_FAILED.getMessage(),
					SocialCrapResponseCode.EMAIL_SENT_FAILED.getCode(), HttpStatus.BAD_REQUEST);
		}
		Email entity = EmailConverter.getEntityFromRequest(request);
		if (StringUtils.isEmpty(entity)) {
			throw new SocialCrapException(SocialCrapResponseCode.EMPTY_REQUEST.getMessage(),
					SocialCrapResponseCode.EMPTY_REQUEST.getCode(), HttpStatus.BAD_REQUEST);
		}
		emailDao.saveT(entity);
		return true;
	}

	@Override
	public Boolean sendSMS(EmailRequest request) throws SocialCrapException {
		if (StringUtils.isEmpty(request)) {
			throw new SocialCrapException(SocialCrapResponseCode.EMPTY_REQUEST.getMessage(),
					SocialCrapResponseCode.EMPTY_REQUEST.getCode(), HttpStatus.BAD_REQUEST);
		}
		userService.validateId(request.getUserId());
		Email entity = EmailConverter.getEntityFromRequest(request);
		if (StringUtils.isEmpty(entity)) {
			throw new SocialCrapException(SocialCrapResponseCode.EMPTY_REQUEST.getMessage(),
					SocialCrapResponseCode.EMPTY_REQUEST.getCode(), HttpStatus.BAD_REQUEST);
		}
		emailDao.saveT(entity);
		return true;
	}

	@Override
	public Boolean update(EmailRequest request, Long id) {
		return null;
	}

	@Override
	public Boolean delete(Long id) throws SocialCrapException {
		return null;
	}

	@Override
	public void validateId(Long id) throws SocialCrapException {

	}

	private List<EmailResponse> getResponseList(List<Email> requestList) {
		if (CollectionUtils.isEmpty(requestList)) {
			return new ArrayList<>();
		}
		List<EmailResponse> responseList = new ArrayList<>();
		responseList = requestList.stream().map(r -> getResponse(r)).collect(Collectors.toList());
		return responseList;
	}

	private EmailResponse getResponse(Email request) {
		if (StringUtils.isEmpty(request)) {
			return null;
		}
		EmailResponse response = EmailConverter.getResponseFromEntity(request);
		if (userDao.isExist(request.getSentBy())) {
			response.setUser(UserConverter.getWrappedEntity(userDao.getTById(request.getSentBy())));
		}
		return response;
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
	public void addAll(List<EmailRequest> requestList) {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateAll(List<EmailRequest> requestList) {
		// TODO Auto-generated method stub

	}

	@Override
	public PaginationResponse<List<EmailResponse>> getAll(String token, Integer limit, Integer offset)
			throws SocialCrapException {
		Long adminId = loginTokenService.validateAdminToken(token);

		List<Email> entityList = emailDao.getAllByAdminId(adminId, limit, PaginationUtil.getOffset(limit, offset));
		PaginationResponse<List<EmailResponse>> responseList = null;
		if (!CollectionUtils.isEmpty(entityList)) {
			responseList = new PaginationResponse<>(getResponseList(entityList));
			return responseList;
		}
		return new PaginationResponse<>(new ArrayList<>());
	}

	@Override
	public Long getTotalCountOfAllByAdminId(String token) throws SocialCrapException {
		Long adminId = loginTokenService.validateAdminToken(token);
		return emailDao.getCountOfAllByAdminId(adminId);
	}

}
