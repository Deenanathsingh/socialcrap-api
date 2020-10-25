package com.socialcrap.api.model.dao.impl;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.apache.commons.collections.CollectionUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;

import com.socialcrap.api.common.enums.Sorting;
import com.socialcrap.api.logs.SocialCrapLogger;
import com.socialcrap.api.model.dao.AbstractDao;
import com.socialcrap.api.model.entity.BaseEntity;

@Transactional
public abstract class AbstractDaoImpl<T> implements AbstractDao<T> {

	@Autowired
	private EntityManager entityManager;

	@Autowired
	private SocialCrapLogger logger;

	protected String entityName;
	protected Class<T> entityClass;
	protected String FROM_ENTITY;
	protected final String ORDER_BY = " ORDER BY %s ";
	protected String UPDATE_ENTITY;

	public AbstractDaoImpl(Class<T> entityClass) {
		this.entityClass = entityClass;
		this.entityName = entityClass.getName();
		this.FROM_ENTITY = "from " + entityName;
		this.UPDATE_ENTITY = "update " + entityName + " set";
	}

	protected Session getSession() {
		return entityManager.unwrap(Session.class);
	}

	@Override
	public void saveT(T entity) {
		getSession().save(entity);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> getAllT() {
		return getSession().createQuery(FROM_ENTITY + " where is_active = true").list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> getAllT(String query) {
		return getSession().createQuery(query).list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> getAllT(String query, int limit, int offset) {
		
		return getSession().createQuery(query).setFirstResult(offset).setMaxResults(limit).list();
	}

	@Override
	public List<T> getAllT(int limit, int offset) {
		logger.info("Get All", null, AbstractDaoImpl.class);
		return getAllT(getQueryObject(FROM_ENTITY), offset, limit);
	}

	protected List<T> getAllT(Query query, Integer offset, Integer limit) {
		if (offset != null) {
			query.setFirstResult(offset);
		}
		if (limit != null) {
			query.setMaxResults(limit);
		}
		return getAllT(query);
	}

	@SuppressWarnings("unchecked")
	protected List<T> getAllT(Query query) {
		return query.list();
	}

	@Override
	public List<T> getAllSorted(Sorting order, String field) {
		String hql = String.format(FROM_ENTITY + " where is_active = true" + ORDER_BY + order.getOrder(), field);
		return getAllT(hql);
	}

	@Override
	public T getTById(Long id) {
		return getSession().get(entityClass, id);
	}

	@Override
	public T getActiveTById(Long id) {
		String hql = FROM_ENTITY
				+ String.format(" where %s=%d and %s=%s", BaseEntity.Table.ID, id, BaseEntity.Table.IS_ACTIVE, true);
		return getT(hql);
	}

	@Override
	public void updateT(T entity) {
		getSession().update(entity);
	}

	@Override
	public void deleteT(Long id) {
		T entity = getTById(id);
		getSession().delete(entity);
	}

	@Override
	public boolean isExist(Long id) {
		return (getTById(id) == null) ? false : true;
	}

	@SuppressWarnings("unchecked")
	@Override
	public T getT(String query) {
		return (T) getSession().createQuery(query).uniqueResult();
	}

	@Override
	public Long getTotalCount() {
		String hql = "SELECT COUNT(id) " + FROM_ENTITY;
		return (Long) getSession().createQuery(hql).uniqueResult();
	}

	@Override
	public void activateAndDeActivate(Long id, boolean status) {
		String hql = UPDATE_ENTITY
				+ String.format(" %s=%s where %s=%d", BaseEntity.Table.IS_ACTIVE, status, BaseEntity.Table.ID, id);
		Query query = getSession().createQuery(hql);
		query.executeUpdate();
	}

	@Override
	public void activate(Long id) {
		String hql = UPDATE_ENTITY
				+ String.format(" %s=%s where %s=%d", BaseEntity.Table.IS_ACTIVE, true, BaseEntity.Table.ID, id);
		Query query = getSession().createQuery(hql);
		query.executeUpdate();
	}

	@Override
	public void deactivate(Long id) {
		String hql = UPDATE_ENTITY
				+ String.format(" %s=%s where %s=%d", BaseEntity.Table.IS_ACTIVE, false, BaseEntity.Table.ID, id);
		Query query = getSession().createQuery(hql);
		query.executeUpdate();
	}

	@Override
	public List<T> getAllByField(String field, String value) {
		String hql = FROM_ENTITY + String.format(" where %s='%s'", field, value);
		return getAllT(hql);
	}

	@Override
	public T getTByField(String field, String value) {
		String hql = FROM_ENTITY + String.format(" where %s='%s'", field, value);
		return getT(hql);
	}

	@Override
	public void saveAll(List<T> requestList) {
		if (CollectionUtils.isEmpty(requestList)) {
			requestList.forEach(r -> {
				saveT(r);
			});
		}
	}

	@Override
	public void updateAll(List<T> requestList) {
		if (CollectionUtils.isEmpty(requestList)) {
			requestList.forEach(r -> {
				updateT(r);
			});
		}
	}

	@Override
	public List<T> search(String searchTerm, String orderBy, Sorting order, String... cols) {
		searchTerm = " like'" + "%" + searchTerm + "%" + "'";
		StringBuilder builder = new StringBuilder();
		builder.append(FROM_ENTITY);
		builder.append(" where ");
		List<String> clauses = Arrays.asList(cols);
		builder.append(clauses.stream().collect(Collectors.joining(searchTerm + " or ", "(", searchTerm + ")")));
		builder.append(" ORDER BY  ");
		builder.append(orderBy);
		builder.append(" ");
		builder.append(order.getOrder());
		return getAllT(builder.toString());
	}

	@Override
	public Query getQueryObject(String hql) {
		return getSession().createQuery(hql);
	}

}
