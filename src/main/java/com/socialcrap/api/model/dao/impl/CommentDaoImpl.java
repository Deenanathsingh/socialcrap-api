package com.socialcrap.api.model.dao.impl;

import org.springframework.stereotype.Repository;

import com.socialcrap.api.model.dao.CommentDao;
import com.socialcrap.api.model.entity.CommentEntity;

@Repository
public class CommentDaoImpl extends AbstractDaoImpl<CommentEntity> implements CommentDao {

	public CommentDaoImpl() {
		super(CommentEntity.class);
	}
}
