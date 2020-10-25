package com.socialcrap.api.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = StatusHistory.Table.TABLE_NAME)
public class StatusHistory extends BaseEntity {

	public interface Table extends BaseEntity.Table {
		String TABLE_NAME = "status_history";
		String REFER_TO = "refer_to";
		String REMARK = "remark";
		String STATUS = "status";
		String TYPE = "type";
		String UPDATED_BY = "updated_by";
	}

	private String remark;
	private String status;
	
	private String type;

	@Column(name = Table.REFER_TO)
	private String referTo;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = Table.UPDATED_BY)
	private User updatedBy;

}
