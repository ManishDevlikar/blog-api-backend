package com.api.blog.services;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.api.blog.paylods.CategoryDto;

@Repository
public interface CategoryServices {
	public CategoryDto createCategory(CategoryDto categoryDto);

	public CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId);

	public void deleteCategory(Integer categaryId);

	public CategoryDto getCategoryById(Integer categoryId);

	public List<CategoryDto> getAllCategory();
}
