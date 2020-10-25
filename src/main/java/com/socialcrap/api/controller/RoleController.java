package com.socialcrap.api.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.socialcrap.api.common.constants.RestMappingConstants;
import com.socialcrap.api.common.enums.SocialCrapResponseCode;
import com.socialcrap.api.common.service.RoleService;
import com.socialcrap.api.exception.SocialCrapException;
import com.socialcrap.api.model.request.dto.RoleRequest;
import com.socialcrap.api.model.response.dto.BaseApiResponse;
import com.socialcrap.api.model.response.dto.RoleResponse;

@RestController
@RequestMapping(value = RestMappingConstants.Role.BASE)
public class RoleController {

	@Autowired
	private RoleService roleService;

	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public BaseApiResponse<List<RoleResponse>> getAll() {
		List<RoleResponse> responseList = roleService.getAll();
		if (CollectionUtils.isEmpty(responseList)) {
			return new BaseApiResponse<>(new ArrayList<>());
		}
		return new BaseApiResponse<>(responseList);
	}

	@GetMapping(path = RestMappingConstants.ID_PARAM)
	@ResponseStatus(HttpStatus.OK)
	public BaseApiResponse<RoleResponse> getById(@PathVariable(RestMappingConstants.ID) Long id)
			throws SocialCrapException {
		RoleResponse response = roleService.getById(id);
		if (response == null) {
			return new BaseApiResponse<>(true, SocialCrapResponseCode.ROLE_ID_NOT_FOUND.getCode(), response,
					String.format(SocialCrapResponseCode.ROLE_ID_NOT_FOUND.getMessage(), id));
		}
		return new BaseApiResponse<>(response);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.OK)
	public BaseApiResponse<Long> add(@Valid @RequestBody RoleRequest request) throws SocialCrapException {
		Long roleId = roleService.add(request);
		return new BaseApiResponse<Long>(roleId);
	}

	@PostMapping(path = RestMappingConstants.Role.SAVE_ALL)
	@ResponseStatus(HttpStatus.OK)
	public BaseApiResponse<Boolean> addAll() throws SocialCrapException {
		roleService.addAll();
		return new BaseApiResponse<>(true);
	}

	@PatchMapping(path = RestMappingConstants.ID_PARAM)
	@ResponseStatus(HttpStatus.OK)
	public BaseApiResponse<Boolean> update(@Valid @RequestBody(required = true) RoleRequest request,
			@PathVariable(RestMappingConstants.ID) Long id) throws SocialCrapException {
		Boolean response = roleService.update(request, id);
		return new BaseApiResponse<>(response);
	}

	@DeleteMapping(path = RestMappingConstants.ID_PARAM)
	@ResponseStatus(HttpStatus.OK)
	public BaseApiResponse<Boolean> delete(@PathVariable(RestMappingConstants.ID) Long id) throws SocialCrapException {
		roleService.validateId(id);
		Boolean response = roleService.delete(id);
		return new BaseApiResponse<>(response);
	}
}
