package com.socialcrap.api.model.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = UserSetting.Table.TABLE_NAME)
public class UserSetting extends BaseEntity{

	public interface Table {
		String TABLE_NAME = "settings";
	}

}
