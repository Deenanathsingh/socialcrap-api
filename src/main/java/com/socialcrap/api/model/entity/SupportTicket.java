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
@Table(name = SupportTicket.Table.TABLE_NAME)
public class SupportTicket extends BaseEntity {

	public interface Table extends BaseEntity.Table {
		String TABLE_NAME = "tickets";
		String TITLE = "title";
		String ISSUE = "issue";
		String ATTACHMENTS = "attachments";
		String TYPE = "type";
		String SENT_BY = "sent_by";
		String CURRENT_STATUS = "current_status";
	}

	private String title;
	private String issue;
	private String attachments;
	private String type;

	@Column(name = Table.CURRENT_STATUS)
	private String currentStatus;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = Table.SENT_BY)
	private User sentBy;

}
