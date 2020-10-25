package com.socialcrap.api.model.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.socialcrap.api.common.constants.EntityDetails;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = EntityDetails.Friend.TABLE_NAME)
public class Friend extends BaseEntity {

	public interface Table {
		String USER_ID = "user_id";
		String FRIEND_ID = "friend_id";
	}

	private Long userId;
	private Long friendId;

	public Friend() {
		setActive(false);
	}

}
