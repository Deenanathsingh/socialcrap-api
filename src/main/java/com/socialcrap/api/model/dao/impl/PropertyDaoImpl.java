package com.socialcrap.api.model.dao.impl;

import com.socialcrap.api.model.dao.PropertyDao;
import com.socialcrap.api.model.entity.Property;

public class PropertyDaoImpl extends AbstractDaoImpl<Property> implements PropertyDao {

	public PropertyDaoImpl() {
		super(Property.class);
	}

	@Override
	public Property getByName(String name) {
		String hql = FROM_ENTITY + String.format(" where %s='%s'", Property.Table.NAME, name);
		return getT(hql);
	}

}
