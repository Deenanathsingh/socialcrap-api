package com.socialcrap.api.model.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = Post.Table.TABLE_NAME)
public class Post extends BaseEntity {

	public interface Table {
		String TABLE_NAME = "posts";
		String JOIN_TABLE_POST_LIKES = "post_likes";
		String POST = "post";
		String TITLE = "title";
		String POSTED_BY = "posted_by";
		String FK_UPLOAD_ID = "fk_upload_id";
		String FK_POST_ID = "fk_post_id";
		String LIKED_BY = "liked_by";
		String FK_TAG_ID = "fk_tag_id";
	}

	@Column(name = Table.TITLE)
	private String title;

	@Column(name = Table.POST, length = 10000)
	private String post;

	@JoinColumn(name = Table.FK_UPLOAD_ID)
	@OneToOne(fetch = FetchType.LAZY)
	private Upload upload;

	@JoinColumn(name = Table.POSTED_BY)
	@ManyToOne(fetch = FetchType.LAZY)
	private User postedBy;

	@OneToMany(mappedBy = Table.POST, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<CommentEntity> comments;

	@ManyToMany
	@JoinTable(name = Table.JOIN_TABLE_POST_LIKES, joinColumns = @JoinColumn(name = Table.FK_POST_ID), inverseJoinColumns = @JoinColumn(name = Table.LIKED_BY))
	private List<User> likedByUsers = new ArrayList<>();

	@ManyToMany
	@JoinTable(name = Table.FK_TAG_ID, joinColumns = @JoinColumn(name = Table.FK_POST_ID), inverseJoinColumns = @JoinColumn(name = Table.LIKED_BY))
	private List<User> tags = new ArrayList<>();
}
