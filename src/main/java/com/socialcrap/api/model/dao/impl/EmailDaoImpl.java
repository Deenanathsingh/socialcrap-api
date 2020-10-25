package com.socialcrap.api.model.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.socialcrap.api.model.dao.EmailDao;
import com.socialcrap.api.model.entity.Email;

@Repository
public class EmailDaoImpl extends AbstractDaoImpl<Email> implements EmailDao {

	public EmailDaoImpl() {
		super(Email.class);
	}

	@Override
	public List<Email> getAllByAdminId(Long adminId, int limit, int offset) {
		String hql = FROM_ENTITY + String.format(" where %s=%s", Email.Table.SENT_BY, adminId);
		return getAllT(hql, limit, offset);
	}

	@Override
	public Long getCountOfAllByAdminId(Long adminId) {
		String hql = "SELECT COUNT(id) " + String.format(FROM_ENTITY + " where %s=%s", Email.Table.SENT_BY, adminId);
		return (Long) getSession().createQuery(hql).uniqueResult();
	}

}
