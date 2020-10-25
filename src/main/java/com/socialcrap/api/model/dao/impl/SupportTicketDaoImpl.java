package com.socialcrap.api.model.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.socialcrap.api.model.dao.SupportTicketDao;
import com.socialcrap.api.model.entity.SupportTicket;

@Repository
public class SupportTicketDaoImpl extends AbstractDaoImpl<SupportTicket>
		implements SupportTicketDao, SupportTicket.Table {

	public SupportTicketDaoImpl() {
		super(SupportTicket.class);
	}

	@Override
	public List<SupportTicket> getAllBySentBy(Long sentById) {
		String hql = String.format(FROM_ENTITY + " where %s = %s", SENT_BY, sentById);
		return getAllT(hql);
	}

	@Override
	public List<SupportTicket> getAllByStatus(String status, int limit, int offset) {
		Query query = getQueryObject(FROM_ENTITY + " where currentStatus =" + status);
		return getAllT(query, offset, limit);
	}

}
