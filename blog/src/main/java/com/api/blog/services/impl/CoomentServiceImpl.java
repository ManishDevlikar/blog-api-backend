package com.api.blog.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.blog.entities.Comment;
import com.api.blog.entities.Post;
import com.api.blog.entities.User;
import com.api.blog.exceptions.ResourceNotFoundException;
import com.api.blog.paylods.CommentDto;
import com.api.blog.repositories.Comment_Repo;
import com.api.blog.repositories.Post_Repo;
import com.api.blog.repositories.User_Repo;
import com.api.blog.services.CommentService;

@Service
public class CoomentServiceImpl implements CommentService {

	@Autowired
	private Comment_Repo comment_Repo;

	@Autowired
	private Post_Repo post_Repo;

	@Autowired
	private User_Repo user_Repo;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public CommentDto createComment(CommentDto commentDto, Integer postId, Integer userId) {
		Post post = post_Repo.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "PostId", postId));
		User user = user_Repo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "UserId", userId));

		Comment comment = this.CommentDtoToComment(commentDto);
		comment.setPost(post);
		comment.setUser(user);
		Comment savedComment = this.comment_Repo.save(comment);
		return this.CommentToCommentDto(savedComment);
	}

	@Override
	public void deleteComment(Integer commentId) {
		Comment comment = this.comment_Repo.findById(commentId)
				.orElseThrow(() -> new ResourceNotFoundException("Comment", "CommentId", commentId));
		this.comment_Repo.deleteById(commentId);
	}

	public CommentDto CommentToCommentDto(Comment comment) {
		return this.modelMapper.map(comment, CommentDto.class);
	}

	public Comment CommentDtoToComment(CommentDto commentDto) {
		return this.modelMapper.map(commentDto, Comment.class);
	}

}
