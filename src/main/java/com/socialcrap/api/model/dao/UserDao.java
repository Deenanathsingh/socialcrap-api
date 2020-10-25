package com.socialcrap.api.model.dao;

import java.util.List;

import com.socialcrap.api.model.entity.User;

public interface UserDao extends AbstractDao<User> {

	public List<User> getAllByIdList(List<Long> requestList);

	public User getLoginDetails(String user);

	public Boolean emailExist(String email);

	public Boolean mobileNumberExist(String mobileNumber);

	public void blockAndUnBlock(Long id, boolean status);
}
