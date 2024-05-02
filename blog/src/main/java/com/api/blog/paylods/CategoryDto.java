package com.api.blog.paylods;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CategoryDto {
	private Integer categoryId;

	@NotEmpty
	@Size(min = 3, message = "title must have more than 3 letter")
	private String categoryTitle;
	@Size(min = 5, message = "description must be more than 5 letters")
	private String categoryDescription;
}
