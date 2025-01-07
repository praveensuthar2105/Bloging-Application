package com.codewithpraveen.blog_app_apis.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codewithpraveen.blog_app_apis.Entites.Category;

public interface CategoryRepo extends JpaRepository<Category, Integer> {

}
