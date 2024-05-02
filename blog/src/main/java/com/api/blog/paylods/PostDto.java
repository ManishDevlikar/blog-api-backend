package com.api.blog.paylods;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PostDto {

	private Integer postId;

	@NotEmpty
	@Size(min = 5, message = "min 5 character")
	private String title;

	@NotEmpty
	@Size(min = 10, message = "min 10 character")
	private String content;

	private String imageName;

	private Date addedDate;

	private UserDto user;

	private CategoryDto category;

	private Set<CommentDto> comments = new HashSet<>();
}
