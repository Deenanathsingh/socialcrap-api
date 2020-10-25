package com.socialcrap.api.model.dao;

import java.util.List;

import com.socialcrap.api.common.enums.Sorting;
import com.socialcrap.api.model.entity.Post;

public interface PostDao extends AbstractDao<Post> {

	List<Post> getAllSortedByUserIds(List<Long> userIds, Sorting order, String field);

}
