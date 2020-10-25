package com.socialcrap.api.model.dao.impl;

import org.springframework.stereotype.Repository;

import com.socialcrap.api.model.dao.RoleDao;
import com.socialcrap.api.model.entity.Role;

@Repository
public class RoleDaoImpl extends AbstractDaoImpl<Role> implements RoleDao {

	public RoleDaoImpl() {
		super(Role.class);
	}
}
