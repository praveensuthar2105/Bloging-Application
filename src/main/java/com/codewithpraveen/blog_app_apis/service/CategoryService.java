package com.codewithpraveen.blog_app_apis.service;

import java.util.List;

import com.codewithpraveen.blog_app_apis.payloads.CategoryDto;

public interface CategoryService {

    public CategoryDto createCategory(CategoryDto categoryDto);

    CategoryDto updateCategory(Integer categoryId, CategoryDto categoryDto);

    void deleteCategory(Integer categoryId);

    CategoryDto getCategory(Integer categoryId);

    List<CategoryDto> getAllCategories();

}
