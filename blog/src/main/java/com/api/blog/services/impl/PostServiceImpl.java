package com.api.blog.services.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.api.blog.entities.Category;
import com.api.blog.entities.Post;
import com.api.blog.entities.User;
import com.api.blog.exceptions.ResourceNotFoundException;
import com.api.blog.paylods.PostDto;
import com.api.blog.paylods.PostResponse;
import com.api.blog.repositories.Category_Repo;
import com.api.blog.repositories.Post_Repo;
import com.api.blog.repositories.User_Repo;
import com.api.blog.services.PostService;

@Service
public class PostServiceImpl implements PostService {

	@Autowired
	private Post_Repo post_Repo;

	@Autowired
	private User_Repo user_Repo;

	@Autowired
	private Category_Repo category_Repo;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public PostDto createPost(PostDto postDto, Integer userId, Integer categoryId) {
		User user = user_Repo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("user", "userId", userId));

		Category category = category_Repo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));
		Post post = this.postDtoToPost(postDto);
		post.setImageName("default.png");
		post.setAddedDate(new Date());
		post.setUser(user);
		post.setCategory(category);

		Post newPost = this.post_Repo.save(post);

		return this.postToPostDto(newPost);
	}

	@Override
	public PostDto updatePost(PostDto postDto, Integer postId) {
		Post post = this.post_Repo.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "PostId", postId));
//		Category category = this.category_Repo.findById(postDto.getCategory().getCategoryId()).get();
		Category category = this.category_Repo.findById(postDto.getCategory().getCategoryId()).get();
		post.setCategory(category);
		post.setTitle(postDto.getTitle());
		post.setContent(postDto.getContent());
		post.setImageName(postDto.getImageName());
		Post updatedPost = this.post_Repo.save(post);
		return this.postToPostDto(updatedPost);
	}

	@Override
	public void deletePost(Integer postId) {
		Post post = this.post_Repo.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "PostId", postId));
		this.post_Repo.delete(post);
	}

	@Override
	public PostDto getPostById(Integer postId) {
		Post post = post_Repo.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "PostId", postId));

		return this.postToPostDto(post);
	}

	@Override
	public PostResponse getAllPost(Integer pageNo, Integer pageSize, String sortBy, String sortDirc) {

		Sort sort = sortDirc.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

		Pageable p = PageRequest.of(pageNo, pageSize, sort);
		Page<Post> pagePost = this.post_Repo.findAll(p);
		List<Post> postList = pagePost.getContent();
		List<PostDto> postDtoList = postList.stream().map(post -> this.postToPostDto(post))
				.collect(Collectors.toList());
		PostResponse postResponse = new PostResponse();
		postResponse.setContent(postDtoList);
		postResponse.setPageNo(pagePost.getNumber());
		postResponse.setPageSize(pagePost.getSize());
		postResponse.setTotalElements(pagePost.getTotalElements());
		postResponse.setTotalPages(pagePost.getTotalPages());
		postResponse.setLastPage(pagePost.isLast());
		return postResponse;
	}

	@Override
	public List<PostDto> getByUser(Integer userId) {
		User user = this.user_Repo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "UserId", userId));
		List<Post> postList = this.post_Repo.findByUser(user);
		List<PostDto> postDtoList = postList.stream().map(post -> this.postToPostDto(post))
				.collect(Collectors.toList());
		return postDtoList;
	}

	@Override
	public List<PostDto> getByCategory(Integer categoryId) {
		Category category = this.category_Repo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("category", "category Id", categoryId));
		List<Post> postList = this.post_Repo.findByCategory(category);
		List<PostDto> postDtoList = postList.stream().map(post -> this.postToPostDto(post))
				.collect(Collectors.toList());
		return postDtoList;
	}

	@Override
	public List<PostDto> searchPostByKeyword(String keyword) {
		List<Post> postList = this.post_Repo.findByTitleContaining(keyword);
		List<PostDto> postListDto = postList.stream().map(post -> this.postToPostDto(post))
				.collect(Collectors.toList());
		return postListDto;
	}

	private Post postDtoToPost(PostDto postDto) {
		return this.modelMapper.map(postDto, Post.class);
	}

	private PostDto postToPostDto(Post post) {
		return this.modelMapper.map(post, PostDto.class);
	}
}
