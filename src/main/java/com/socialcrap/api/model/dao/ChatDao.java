package com.socialcrap.api.model.dao;

import java.util.List;

import com.socialcrap.api.model.entity.ChatEntity;

public interface ChatDao extends AbstractDao<ChatEntity>{
	
	public List<ChatEntity> getBySentToAndSentBy(Long sentToId,Long sentById);

}
