package com.socialcrap.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.socialcrap.api.common.constants.RestMappingConstants;
import com.socialcrap.api.exception.SocialCrapException;
import com.socialcrap.api.model.response.dto.BaseApiResponse;

public interface BaseController<Request, Response> {

	public BaseApiResponse<List<Response>> getAll();

	public BaseApiResponse<Response> getById(@PathVariable(RestMappingConstants.ID) Long id) throws SocialCrapException;

	public BaseApiResponse<Long> add(@Valid @RequestBody(required = true) Request request) throws SocialCrapException;

	public BaseApiResponse<Boolean> update(@RequestBody(required = true) Request request, @PathVariable(RestMappingConstants.ID) Long id) throws SocialCrapException;

	public BaseApiResponse<Boolean> delete(@PathVariable(RestMappingConstants.ID) Long id) throws SocialCrapException;
}
