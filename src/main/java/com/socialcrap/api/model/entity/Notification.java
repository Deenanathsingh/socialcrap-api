package com.socialcrap.api.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = Notification.Table.TABLE_NAME)
public class Notification extends BaseEntity {

	public interface Table {
		String TABLE_NAME="notifications";
		String NOTIFICATION = "notification";
		String SENT_TO = "sent_to";
		String SENT_BY = "sent_by";
	}

	@Column(name = Table.NOTIFICATION, length = 500)
	private String notification;

	@JoinColumn(name = Table.SENT_TO)
	@ManyToOne(fetch = FetchType.LAZY)
	private User sentTo;

	@JoinColumn(name = Table.SENT_BY)
	@ManyToOne(fetch = FetchType.LAZY)
	private User sentBy;
}
