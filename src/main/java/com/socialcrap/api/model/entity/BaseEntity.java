package com.socialcrap.api.model.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Data;

@Data
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity {

	public interface Table {
		String ID = "id";
		String CREATED = "created";
		String UPDATED = "updated";
		String IS_ACTIVE = "is_active";
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = Table.ID, nullable = false, updatable = false)
	private Long Id;
	
	@Temporal(TemporalType.TIMESTAMP)
	@CreationTimestamp
	@Column(name = Table.CREATED, nullable = false, updatable = false)
	private Date created;
	
	@Temporal(TemporalType.TIMESTAMP)
	@LastModifiedDate
	@Column(name = Table.UPDATED, nullable = false)
	private Date updated;
	
	@Column(name = Table.IS_ACTIVE)
	private boolean isActive;
	
}
