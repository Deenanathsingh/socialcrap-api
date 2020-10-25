package com.socialcrap.api.model.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = User.Table.TABLE_NAME)
public class User extends BaseEntity {

	public interface Table {
		String TABLE_NAME = "users";
		String JOIN_TABLE_FOLLOWER = "followers";
		String FIRST_NAME = "first_name";
		String LAST_NAME = "last_name";
		String EMAIL = "email";
		String MOBILE_NUMBER = "mobile_number";
		String ADDRESS = "address";
		String PASSWORD = "password";
		String ABOUT = "about";
		String RECOVERY_DETAILS = "recovery_details";
		String PROFILE_PHOTO_LINK = "profile_photo_link";
		String COVER_PHOTO_LINK = "cover_photo_link";
		String IS_BLOCKED = "is_blocked";
		String FK_ROLE_ID = "fk_role_id";
		String FK_SETTING_ID = "fk_setting_id";
		String CREATED_ORDER_DSC = "created DESC";
		String JOIN_COLUMN_FOLLOWING = "following";
		String JOIN_COLUMN_FOLLOWER = "follower";
	}

	@Column(name = Table.FIRST_NAME)
	private String firstName;
	@Column(name = Table.LAST_NAME)
	private String lastName;
	@Column(name = Table.EMAIL)
	private String email;
	@Column(name = Table.MOBILE_NUMBER)
	private String mobileNumber;
	@Column(name = Table.ADDRESS)
	private String address;
	@Column(name = Table.PASSWORD)
	private String password;
	@Column(name = Table.ABOUT)
	private String about;
	@Column(name = Table.RECOVERY_DETAILS)
	private String recoveryDetails;
	@Column(name = Table.PROFILE_PHOTO_LINK)
	private String profilePhotoLink;
	@Column(name = Table.COVER_PHOTO_LINK)
	private String coverPhotoLink;
	@Column(name = Table.IS_BLOCKED)
	private boolean isBlocked;

	@JoinColumn(name = Table.FK_ROLE_ID)
	@Cascade(CascadeType.SAVE_UPDATE)
	@ManyToOne(fetch = FetchType.LAZY)
	private Role role;

	@JoinColumn(name = Table.FK_SETTING_ID)
	@OneToOne(fetch = FetchType.LAZY)
	private UserSetting settings;

	@OneToMany(mappedBy = "sentTo", fetch = FetchType.LAZY)
	@OrderBy(value = Table.CREATED_ORDER_DSC)
	private List<Notification> notifications = new ArrayList<>();

	@OneToMany(mappedBy = "postedBy", fetch = FetchType.LAZY)
	@OrderBy(value = Table.CREATED_ORDER_DSC)
	private List<Post> post = new ArrayList<>();

//	@ManyToMany(fetch = FetchType.LAZY)
//	@JoinTable(name = Table.JOIN_TABLE_FOLLOWER, joinColumns = @JoinColumn(name = Table.JOIN_COLUMN_FOLLOWING), inverseJoinColumns = @JoinColumn(name = Table.JOIN_COLUMN_FOLLOWER))
//	private List<User> followers = new ArrayList<>();
}
