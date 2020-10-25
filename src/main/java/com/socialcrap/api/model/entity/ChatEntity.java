package com.socialcrap.api.model.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
@Table(name = ChatEntity.Table.TABLE_NAME)
public class ChatEntity extends BaseEntity {

	public interface Table {
		String TABLE_NAME = "chats";
		String SENT_BY = "sent_by";
		String SENT_TO = "sent_to";
	}

	private String message;
	
	@JoinColumn(name = Table.SENT_BY)
	@ManyToOne(fetch = FetchType.LAZY)
	private User sentBy;
	
	@JoinColumn(name = Table.SENT_TO)
	@ManyToOne(fetch = FetchType.LAZY)
	private User sentTo;
}
