package com.socialcrap.api.model.dao;

import java.util.List;

import org.hibernate.Query;

import com.socialcrap.api.common.enums.Sorting;

public interface AbstractDao<T> {

	public void saveT(T entity);

	public List<T> getAllT();

	public List<T> getAllT(String query);

	public List<T> getAllSorted(Sorting order, String field);

	public T getTById(Long id);

	public List<T> getAllByField(String field, String value);

	public T getTByField(String field, String value);

	public T getActiveTById(Long id);

	public void updateT(T entity);

	public void deleteT(Long id);

	public boolean isExist(Long id);

	public T getT(String query);

	public List<T> getAllT(int limit, int offset);

	public List<T> getAllT(String query, int limit, int offset);

	public Long getTotalCount();

	public void activateAndDeActivate(Long id, boolean status);

	public void activate(Long id);

	public void deactivate(Long id);

	public void saveAll(List<T> list);

	public void updateAll(List<T> list);

	public List<T> search(String searchTerm, String orderBy, Sorting order, String... cols);

	public Query getQueryObject(String hql);
}
