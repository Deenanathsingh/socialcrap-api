package com.socialcrap.api.model.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.socialcrap.api.model.dao.ChatDao;
import com.socialcrap.api.model.entity.ChatEntity;

@Repository
public class ChatDaoImpl extends AbstractDaoImpl<ChatEntity> implements ChatDao {

	public ChatDaoImpl() {
		super(ChatEntity.class);
	}

	@Override
	public List<ChatEntity> getBySentToAndSentBy(Long sentToId, Long sentById) {
		String hql = FROM_ENTITY + String.format(" where %s=%s and %s=%s", ChatEntity.Table.SENT_BY, sentById,
				ChatEntity.Table.SENT_TO, sentToId);
		return getAllT(hql);
	}

}
