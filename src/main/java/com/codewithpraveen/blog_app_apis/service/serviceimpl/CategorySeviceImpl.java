package com.codewithpraveen.blog_app_apis.service.serviceimpl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codewithpraveen.blog_app_apis.Entites.Category;
import com.codewithpraveen.blog_app_apis.exceptions.ResourceNotFoundException;
import com.codewithpraveen.blog_app_apis.payloads.CategoryDto;
import com.codewithpraveen.blog_app_apis.repository.CategoryRepo;
import com.codewithpraveen.blog_app_apis.service.CategoryService;

@Service
public class CategorySeviceImpl implements CategoryService {

    @Autowired
    private CategoryRepo categoryRepo;


    @Autowired
    private ModelMapper  modelMapper;

    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {
       
        Category cat = this.modelMapper.map(categoryDto, Category.class);
        Category savedCategory = this.categoryRepo.save(cat);
        
        return this.modelMapper.map(savedCategory, CategoryDto.class);

    }

    @Override
    public CategoryDto updateCategory(Integer categoryId, CategoryDto categoryDto) {
       
        Category cat = this.categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category","Category Id", categoryId));

        cat.setCategoryTitle(categoryDto.getCategoryTitle());

        Category updatedCategory = this.categoryRepo.save(cat);
        return this.modelMapper.map(updatedCategory, CategoryDto.class);
    }

    @Override
    public void deleteCategory(Integer categoryId) {
       
        Category cat = this.categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category","Category Id", categoryId));
        this.categoryRepo.delete(cat);
    }

    @Override
    public CategoryDto getCategory(Integer categoryId) {
         
        Category cat = this.categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category","Category Id", categoryId));
        return this.modelMapper.map(cat, CategoryDto.class);

    }

    @Override
    public List<CategoryDto> getAllCategories() {
       
        List<Category> categories = this.categoryRepo.findAll();
       
          List<CategoryDto> catDto =  categories.stream().map(cat -> this.modelMapper.map(cat, CategoryDto.class)).collect(Collectors.toList());
        return catDto;

    }

    



} 

