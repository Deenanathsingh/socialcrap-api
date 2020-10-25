package com.socialcrap.api.common.service;

import java.util.List;

import com.socialcrap.api.exception.SocialCrapException;

public interface AbstractService<Response, Request> {

	public List<Response> getAll();

	public Response getById(Long id);

	public Long add(Request request) throws SocialCrapException;

	public Boolean update(Request request, Long id) throws SocialCrapException;

	public Boolean delete(Long id) throws SocialCrapException;

	public void validateId(Long id) throws SocialCrapException;

	public void activate(Long id) throws SocialCrapException;

	public void deactivate(Long id) throws SocialCrapException;

	public void addAll(List<Request> requestList);

	public void updateAll(List<Request> requestList);
}
