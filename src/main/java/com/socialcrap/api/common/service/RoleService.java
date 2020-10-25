package com.socialcrap.api.common.service;

import com.socialcrap.api.model.request.dto.RoleRequest;
import com.socialcrap.api.model.response.dto.RoleResponse;

public interface RoleService extends AbstractService<RoleResponse, RoleRequest> {

	public void addAll();
}
