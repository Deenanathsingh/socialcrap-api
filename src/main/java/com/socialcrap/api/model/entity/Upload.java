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
@Table(name = Upload.Table.TABLE_NAME)
public class Upload extends BaseEntity {

	public interface Table {
		String TABLE_NAME = "uploads";
		String URL = "url";
		String TYPE = "type";
		String UPLOADED_BY = "uploaded_by";
	}

	@Column(name = Table.URL)
	private String url;

	@Column(name = Table.TYPE)
	private String type;

	@JoinColumn(name = Table.UPLOADED_BY)
	@ManyToOne(fetch = FetchType.LAZY)
	private User uploadedBy;

}
