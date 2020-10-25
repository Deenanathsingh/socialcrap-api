package com.socialcrap.api.model.entity;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.socialcrap.api.common.enums.LikeOrDisLike;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
//@Entity
//@Table(name = EntityDetails.CommentLike.TABLE_NAME)
public class CommentLike extends BaseEntity {

	public Long commentId;
	public Long userId;
	@Enumerated(EnumType.STRING)
	@Column(columnDefinition = LikeOrDisLike.COL_DEF)
	public LikeOrDisLike likeOrDisLike;

}
