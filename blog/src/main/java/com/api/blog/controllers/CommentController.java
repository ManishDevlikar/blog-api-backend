package com.api.blog.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.blog.paylods.ApiResponse;
import com.api.blog.paylods.CommentDto;
import com.api.blog.services.CommentService;

@RestController
@RequestMapping("/api/comment")
public class CommentController {

	@Autowired
	private CommentService commentService;

	@PostMapping("/{postId}/{userId}")
	private ResponseEntity<CommentDto> createComment(@RequestBody CommentDto commentDto, @PathVariable Integer postId,
			@PathVariable Integer userId) {
		return new ResponseEntity<CommentDto>(this.commentService.createComment(commentDto, postId, userId),
				HttpStatus.CREATED);

	}

	@DeleteMapping("/{commentId}")
	private ResponseEntity<ApiResponse> deleteComment(@PathVariable Integer commentId) {
		this.commentService.deleteComment(commentId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("comment removed Successfully", true), HttpStatus.OK);
	}

}
