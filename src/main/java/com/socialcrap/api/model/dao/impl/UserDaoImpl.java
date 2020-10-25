package com.socialcrap.api.model.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.socialcrap.api.model.dao.UserDao;
import com.socialcrap.api.model.entity.BaseEntity;
import com.socialcrap.api.model.entity.User;

@Repository
public class UserDaoImpl extends AbstractDaoImpl<User> implements UserDao {

	public UserDaoImpl() {
		super(User.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<User> getAllByIdList(List<Long> requestList) {
		Criteria criteria = getSession().createCriteria(entityClass);
		criteria.add(Restrictions.in("id", requestList));
		criteria.addOrder(Order.asc("firstName"));
		return criteria.list();
	}

	@Override
	public User getLoginDetails(String user) {
		String hql = String.format(FROM_ENTITY + " where %s='%s' OR %s='%s'", User.Table.EMAIL, user,
				User.Table.MOBILE_NUMBER, user);
		return getT(hql);
	}

	@Override
	public Boolean emailExist(String email) {
		String hql = String.format(FROM_ENTITY + " where %s='%s'", User.Table.EMAIL, email);
		return (getT(hql) == null) ? false : true;
	}

	@Override
	public Boolean mobileNumberExist(String mobileNumber) {
		String hql = String.format(FROM_ENTITY + " where %s='%s'", User.Table.MOBILE_NUMBER, mobileNumber);
		return (getT(hql) == null) ? false : true;
	}

	@Override
	public void blockAndUnBlock(Long id, boolean status) {
		String hql = UPDATE_ENTITY
				+ String.format(" %s=%s where %s=%d", User.Table.IS_BLOCKED, status, BaseEntity.Table.ID, id);
		Query query = getSession().createQuery(hql);
		query.executeUpdate();
	}
}
