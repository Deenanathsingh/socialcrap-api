package com.socialcrap.api.model.dao;

import java.util.List;

import com.socialcrap.api.model.entity.SupportTicket;

public interface SupportTicketDao extends AbstractDao<SupportTicket> {

	List<SupportTicket> getAllBySentBy(Long sentById);

	List<SupportTicket> getAllByStatus(String status, int limit, int offset);

}
