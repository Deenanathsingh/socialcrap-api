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
import com.socialcrap.api.common.converter.RoleConverter;
import com.socialcrap.api.common.enums.Roles;
import com.socialcrap.api.common.enums.SocialCrapResponseCode;
import com.socialcrap.api.common.enums.Sorting;
import com.socialcrap.api.common.service.RoleService;
import com.socialcrap.api.exception.SocialCrapException;
import com.socialcrap.api.model.dao.RoleDao;
import com.socialcrap.api.model.entity.Role;
import com.socialcrap.api.model.request.dto.RoleRequest;
import com.socialcrap.api.model.response.dto.RoleResponse;

@Service
public class RoleServiceImpl implements RoleService {

	@Autowired
	private RoleDao roleDao;

	@Override
	public List<RoleResponse> getAll() {
		return RoleConverter
				.getResponseListFromEntityList(roleDao.getAllSorted(Sorting.DESC, EntityDetails.Role.CREATED_DATE));
	}

	@Override
	public RoleResponse getById(Long id) {
		return RoleConverter.getResponseFromEntity(roleDao.getTById(id));
	}

	@Override
	public Long add(RoleRequest request) throws SocialCrapException {
		if (StringUtils.isEmpty(request)) {
			throw new SocialCrapException(SocialCrapResponseCode.EMPTY_REQUEST.getMessage(),
					SocialCrapResponseCode.EMPTY_REQUEST.getCode(), HttpStatus.BAD_REQUEST);
		}
		Role entity = RoleConverter.getEntityFromRequest(request);
		if (StringUtils.isEmpty(entity)) {
			throw new SocialCrapException(SocialCrapResponseCode.EMPTY_REQUEST.getMessage(),
					SocialCrapResponseCode.EMPTY_REQUEST.getCode(), HttpStatus.BAD_REQUEST);
		}
		roleDao.saveT(entity);
		return entity.getId();
	}

	@Override
	public Boolean update(RoleRequest request, Long id) throws SocialCrapException {
		if (StringUtils.isEmpty(request)) {
			throw new SocialCrapException(SocialCrapResponseCode.EMPTY_REQUEST.getMessage(),
					SocialCrapResponseCode.EMPTY_REQUEST.getCode(), HttpStatus.BAD_REQUEST);
		}
		if (roleDao.isExist(id)) {
			Role entity = RoleConverter.getEntityFromRequest(request);
			roleDao.updateT(entity);
			return true;
		}
		return false;
	}

	@Override
	public Boolean delete(Long id) throws SocialCrapException {
		validateId(id);
		roleDao.deleteT(id);
		return null;
	}

	@Override
	public void validateId(Long id) throws SocialCrapException {
		if (!roleDao.isExist(id)) {
			String message = String.format(SocialCrapResponseCode.ROLE_ID_NOT_FOUND.getMessage(), id);
			throw new SocialCrapException(message, SocialCrapResponseCode.ROLE_ID_NOT_FOUND.getCode(),
					HttpStatus.NOT_FOUND);
		}
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
	public void addAll() {
		List<Role> entityList = new ArrayList<>();
		Roles[] roles = Roles.values();
		for (int i = 0; i < roles.length; i++) {
			Role entity = new Role();
			entity.setRole(roles[i].getRole());
			entityList.add(entity);
		}
		roleDao.saveAll(entityList);
	}

	@Override
	public void addAll(List<RoleRequest> requestList) {
		if (CollectionUtils.isEmpty(requestList)) {
			return;
		}
		List<Role> entityList = requestList.stream().map(r -> RoleConverter.getEntityFromRequest(r))
				.collect(Collectors.toList());
		roleDao.saveAll(entityList);
	}

	@Override
	public void updateAll(List<RoleRequest> requestList) {
		
	}

}
