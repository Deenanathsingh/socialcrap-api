package com.socialcrap.api.common.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.socialcrap.api.common.converter.FollowerConverter;
import com.socialcrap.api.common.service.FollowerService;
import com.socialcrap.api.exception.SocialCrapException;
import com.socialcrap.api.model.dao.FollowerDao;
import com.socialcrap.api.model.entity.Follower;
import com.socialcrap.api.model.request.dto.FollowerRequest;
import com.socialcrap.api.model.response.dto.FollowerResponse;

@Service
public class FollowerServiceImpl implements FollowerService {

	@Autowired
	private FollowerDao followerDao;

	@Override
	public List<FollowerResponse> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public FollowerResponse getById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long add(FollowerRequest request) throws SocialCrapException {
		if (StringUtils.isEmpty(request)) {
			return null;
		}
		Follower follower = null;
		follower = followerDao.getByUserIdAndFollowerId(request.getUserId(), request.getFollowerId());
		if (!StringUtils.isEmpty(follower)) {
			if (follower.isActive()) {
				follower.setActive(false);
			} else {
				follower.setActive(true);
			}
			followerDao.updateT(follower);
			return follower.getId();
		}
		follower = FollowerConverter.getEntityFromFriendShipRequest(request.getUserId(), request.getFollowerId());
		if (follower == null) {
			return null;
		}
		followerDao.saveT(follower);
		return follower.getId();
	}

	@Override
	public Boolean update(FollowerRequest request, Long id) throws SocialCrapException {
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
	public void addAll(List<FollowerRequest> requestList) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateAll(List<FollowerRequest> requestList) {
		// TODO Auto-generated method stub
		
	}

}
