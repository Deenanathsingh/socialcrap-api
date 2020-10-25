package com.socialcrap.api.model.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.socialcrap.api.common.enums.Sorting;
import com.socialcrap.api.model.dao.PostDao;
import com.socialcrap.api.model.entity.BaseEntity;
import com.socialcrap.api.model.entity.Post;

@Repository
public class PostDaoImpl extends AbstractDaoImpl<Post> implements PostDao {

	public PostDaoImpl() {
		super(Post.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Post> getAllSortedByUserIds(List<Long> userIds, Sorting order, String field) {
		Query query = getSession().createQuery(FROM_ENTITY + " where " + BaseEntity.Table.IS_ACTIVE + "=:ac  and "
				+ Post.Table.POSTED_BY + " in (:uIds) order by :ob " + order.getOrder());
		query.setParameter("ac", true);
		query.setParameter("ob", field);
		query.setParameterList("uIds", userIds);
		return query.list();
	}

}
