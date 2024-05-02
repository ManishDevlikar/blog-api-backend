package com.api.blog.services;

import org.springframework.stereotype.Repository;

import com.api.blog.paylods.CommentDto;

@Repository
public interface CommentService {

	CommentDto createComment(CommentDto commentDto, Integer postId, Integer userId);

	void deleteComment(Integer commentId);
}
