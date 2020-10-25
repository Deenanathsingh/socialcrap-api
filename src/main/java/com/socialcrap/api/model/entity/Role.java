package com.socialcrap.api.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = Role.Table.TABLE_NAME)
public class Role extends BaseEntity {

	public interface Table {
		String TABLE_NAME = "roles";
		String ROLE = "role";
	}

	@Column(name = Table.ROLE)
	private String role;
}
