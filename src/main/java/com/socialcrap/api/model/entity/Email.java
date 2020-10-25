package com.socialcrap.api.model.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.socialcrap.api.common.constants.EntityDetails;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = EntityDetails.Email.TABLE_NAME)
public class Email extends BaseEntity {

	public interface Table extends BaseEntity.Table {
		String RECEIVER = "to";
		String CC = "cc";
		String BCC = "bcc";
		String SUBJECT = "subject";
		String MESSAGE = "message";
		String ATTACHMENTS = "attachments";
		String SENT_BY = "sent_by";
	}

	private String receiver;
	private String cc;
	private String bcc;
	private String subject;
	private String message;
	private String attachments;
	private Long sentBy;
}
