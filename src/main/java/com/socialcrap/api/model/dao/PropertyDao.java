package com.socialcrap.api.model.dao;

import com.socialcrap.api.model.entity.Property;

public interface PropertyDao extends AbstractDao<Property> {

	public Property getByName(String name);

}
