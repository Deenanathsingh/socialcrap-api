package com.socialcrap.api.common.service.impl;

import java.util.List;

import com.socialcrap.api.common.service.PropertyService;
import com.socialcrap.api.exception.SocialCrapException;
import com.socialcrap.api.model.request.dto.PropertyRequest;
import com.socialcrap.api.model.response.dto.PropertyResponse;

public class PropertyServiceImpl implements PropertyService {

	@Override
	public List<PropertyResponse> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PropertyResponse getById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long add(PropertyRequest request) throws SocialCrapException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean update(PropertyRequest request, Long id) throws SocialCrapException {
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
	public void addAll(List<PropertyRequest> requestList) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateAll(List<PropertyRequest> requestList) {
		// TODO Auto-generated method stub
		
	}

}
