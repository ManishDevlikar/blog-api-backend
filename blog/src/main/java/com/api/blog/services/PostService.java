package com.api.blog.services;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.api.blog.paylods.PostDto;
import com.api.blog.paylods.PostResponse;

@Repository
public interface PostService {
	PostDto createPost(PostDto postDto, Integer UserId, Integer categoryId);

	PostDto updatePost(PostDto postDto, Integer postId);

	void deletePost(Integer postId);

	PostDto getPostById(Integer postId);

	PostResponse getAllPost(Integer pageNo, Integer pageSize, String sortBy, String dirc);

	List<PostDto> getByUser(Integer userId);

	List<PostDto> getByCategory(Integer categoryId);

	List<PostDto> searchPostByKeyword(String keyword);

}
