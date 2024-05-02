package com.api.blog.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.blog.entities.Category;
import com.api.blog.exceptions.ResourceNotFoundException;
import com.api.blog.paylods.CategoryDto;
import com.api.blog.repositories.Category_Repo;
import com.api.blog.services.CategoryServices;

@Service
public class CategoryServiceImpl implements CategoryServices {

	@Autowired
	private Category_Repo category_Repo;
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public CategoryDto createCategory(CategoryDto categoryDto) {
		Category category = this.categoryDtoToCategory(categoryDto);
		Category savedCategory = category_Repo.save(category);
		return this.categoryToCategoryDto(savedCategory);
	}

	@Override
	public CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId) {
		Category category = this.category_Repo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("category", "categoryId", categoryId));
		category.setCategoryTitle(categoryDto.getCategoryTitle());
		category.setCategoryDescription(categoryDto.getCategoryDescription());
		Category updatedCategory = category_Repo.save(category);
		return this.categoryToCategoryDto(updatedCategory);
	}

	@Override
	public void deleteCategory(Integer categaryId) {
		Category category = this.category_Repo.findById(categaryId)
				.orElseThrow(() -> new ResourceNotFoundException("category", "categoryId", categaryId));
		this.category_Repo.delete(category);
	}

	@Override
	public CategoryDto getCategoryById(Integer categoryId) {
		Category category = this.category_Repo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "CategoryId", categoryId));
		return this.categoryToCategoryDto(category);
	}

	@Override
	public List<CategoryDto> getAllCategory() {
		List<Category> categoryList = this.category_Repo.findAll();
		List<CategoryDto> categoryDtoList = categoryList.stream().map(category -> this.categoryToCategoryDto(category))
				.collect(Collectors.toList());
		return categoryDtoList;
	}

	public Category categoryDtoToCategory(CategoryDto categoryDto) {
		return this.modelMapper.map(categoryDto, Category.class);
	}

	public CategoryDto categoryToCategoryDto(Category category) {
		return this.modelMapper.map(category, CategoryDto.class);
	}
}
