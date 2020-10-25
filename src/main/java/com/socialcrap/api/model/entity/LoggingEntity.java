package com.socialcrap.api.model.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = LoggingEntity.Table.TABLE_NAME)
public class LoggingEntity extends BaseEntity {

	public interface Table {
		String TABLE_NAME = "logging";
	}

	private String message;
	private String type;
	private String entity;

}
