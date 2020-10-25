package com.socialcrap.api.model.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.socialcrap.api.common.constants.EntityDetails;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = EntityDetails.LoginToken.TABLE_NAME)
public class LoginToken extends BaseEntity {
	private String token;
	private Long expiry;
	@OneToOne(cascade = CascadeType.ALL)
	private User user;
}
