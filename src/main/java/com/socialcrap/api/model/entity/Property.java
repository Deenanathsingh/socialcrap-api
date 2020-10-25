package com.socialcrap.api.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = Property.Table.TABLE_NAME)
public class Property extends BaseEntity {

	public interface Table {
		String TABLE_NAME = "properties";
		String NAME = "name";
		String VALUE = "value";
	}

	@Column(name = Table.NAME, unique = true)
	private String name;

	@Column(name = Table.VALUE)
	private String value;
}
