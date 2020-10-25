package com.socialcrap.api.model.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = CommentEntity.Table.TABLE_NAME)
public class CommentEntity extends BaseEntity {

	public interface Table {
		String TABLE_NAME = "comments";
		String JOIN_TABLE_COMMENT_LIKES = "comment_likes";
		String COMMENT = "comment";
		String COMMENTED_BY = "commented_by";
		String LIKED_BY = "liked_by";
		String FK_POST_ID = "fk_post_id";
		String FK_COMMENT_ID = "fk_comment_id";
	}

	@Column(name = Table.COMMENT, length = 10000)
	private String comment;

	@JoinColumn(name = Table.FK_POST_ID)
	@ManyToOne(fetch = FetchType.LAZY)
	private Post post;

	@JoinColumn(name = Table.COMMENTED_BY)
	@ManyToOne(fetch = FetchType.LAZY)
	private User commentedBy;

	@OneToMany(fetch = FetchType.LAZY)
	@JoinTable(name = Table.JOIN_TABLE_COMMENT_LIKES, joinColumns = @JoinColumn(name = Table.FK_COMMENT_ID), inverseJoinColumns = @JoinColumn(name = Table.LIKED_BY))
	private List<User> likedByUsers = new ArrayList<>();

}
