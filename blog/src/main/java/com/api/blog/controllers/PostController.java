package com.api.blog.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.api.blog.config.AppConstans;
import com.api.blog.paylods.ApiResponse;
import com.api.blog.paylods.PostDto;
import com.api.blog.paylods.PostResponse;
import com.api.blog.services.FileService;
import com.api.blog.services.PostService;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/")
public class PostController {

	@Autowired
	private PostService postService;

	@Autowired
	private FileService fileService;

	@Value("${project.image}")
	private String path;

	@PostMapping("/user/{userId}/category/{categoryId}/post")
	public ResponseEntity<PostDto> createPost(@RequestBody PostDto postdto, @PathVariable Integer userId,
			@PathVariable Integer categoryId) {
		return new ResponseEntity<PostDto>(this.postService.createPost(postdto, userId, categoryId),
				HttpStatus.CREATED);
	}

	@PutMapping("/posts/{postId}")
	public ResponseEntity<PostDto> updatePostById(@RequestBody PostDto postDto, @PathVariable Integer postId) {
		return new ResponseEntity<PostDto>(this.postService.updatePost(postDto, postId), HttpStatus.OK);
	}

	@DeleteMapping("/posts/{postId}")
	public ResponseEntity<ApiResponse> deletePostById(@PathVariable Integer postId) {
		this.postService.deletePost(postId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("post deleted succsefully", true), HttpStatus.OK);
	}

	@GetMapping("posts/{postId}")
	public ResponseEntity<PostDto> getPostById(@PathVariable Integer postId) {
		return new ResponseEntity<PostDto>(this.postService.getPostById(postId), HttpStatus.OK);
	}

	@GetMapping("/posts")
	public ResponseEntity<PostResponse> getAllPost(
			@RequestParam(value = "pageNo", defaultValue = AppConstans.PAGE_NO, required = false) Integer pageNo,
			@RequestParam(value = "pageSize", defaultValue = AppConstans.PAGE_SIZE, required = false) Integer pageSize,
			@RequestParam(value = "sortBy", defaultValue = AppConstans.SORT_BY, required = false) String sortBy,
			@RequestParam(value = "sortDirc", defaultValue = AppConstans.SORT_DIRC, required = false) String sortDirc) {
		return new ResponseEntity<PostResponse>(this.postService.getAllPost(pageNo, pageSize, sortBy, sortDirc),
				HttpStatus.OK);
	}

	@GetMapping("category/{categoryId}/post")
	public ResponseEntity<List<PostDto>> getPostByCategory(@PathVariable Integer categoryId) {
		return ResponseEntity.ok(this.postService.getByCategory(categoryId));
	}

	@GetMapping("user/{userId}/post")
	public ResponseEntity<List<PostDto>> getPostByUser(@PathVariable Integer userId) {
		return ResponseEntity.ok(this.postService.getByUser(userId));
	}

	@GetMapping("/post/search/{keywords}")
	public ResponseEntity<List<PostDto>> getPostByKeyword(@PathVariable String keywords) {
		return new ResponseEntity<List<PostDto>>(this.postService.searchPostByKeyword(keywords), HttpStatus.OK);
	}

	/////////////////////
	// image uploading
	///////////////////
	@PostMapping("/post/image/upload/{postId}")
	public ResponseEntity<PostDto> uploadPostImage(@RequestParam MultipartFile image, @PathVariable Integer postId)
			throws IOException {
		PostDto post = this.postService.getPostById(postId);
		String fileName = this.fileService.uploadImage(path, image);
		post.setImageName(fileName);
		PostDto updatedPost = this.postService.updatePost(post, postId);
		return new ResponseEntity<PostDto>(updatedPost, HttpStatus.OK);
	}

	////////////////
	// to serve file
	////////////////
	@GetMapping(value = "post/image/{imageName}", produces = MediaType.IMAGE_PNG_VALUE)
	public void downloadFile(@PathVariable String imageName, HttpServletResponse servletResponse) throws IOException {
		InputStream resource = this.fileService.getResource(path, imageName);
		servletResponse.setContentType(MediaType.IMAGE_PNG_VALUE);
		StreamUtils.copy(resource, servletResponse.getOutputStream());
	}

}
