package com.socialcrap.api.model.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.socialcrap.api.common.constants.EntityDetails;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = EntityDetails.Follower.TABLE_NAME)
public class Follower extends BaseEntity {
	private Long userId;
	private Long followerId;
	private Boolean notificationEnabled;
	private Boolean postEnabled;

	public Follower() {
		this.notificationEnabled = true;
		this.postEnabled = true;
	}
}
