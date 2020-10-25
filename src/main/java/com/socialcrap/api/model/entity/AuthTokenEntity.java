package com.socialcrap.api.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = AuthTokenEntity.Table.TABLE_NAME)
public class AuthTokenEntity extends BaseEntity {

	public interface Table extends BaseEntity.Table {
		String TABLE_NAME = "auth_token";
		String TOKEN = "token";
		String USER_ID = "user_id";
		String LOGIN_TYPE = "login_type";
	}

	@Column(name = Table.TOKEN, nullable = false)
	private String token;

	@Column(name = Table.USER_ID, nullable = false)
	private Long userId;

	@Column(name = Table.LOGIN_TYPE, nullable = false)
	private String loginType;
}
