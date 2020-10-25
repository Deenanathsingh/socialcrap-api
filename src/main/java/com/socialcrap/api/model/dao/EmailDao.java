package com.socialcrap.api.model.dao;

import java.util.List;

import com.socialcrap.api.model.entity.Email;

public interface EmailDao extends AbstractDao<Email> {

	public List<Email> getAllByAdminId(Long adminId, int limit, int offset);

	public Long getCountOfAllByAdminId(Long adminId);

}
