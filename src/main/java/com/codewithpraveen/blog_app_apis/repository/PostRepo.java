package com.codewithpraveen.blog_app_apis.repository;

import java.util.List;
import com.codewithpraveen.blog_app_apis.Entites.Category;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.codewithpraveen.blog_app_apis.Entites.Post;
import com.codewithpraveen.blog_app_apis.Entites.User;

import org.springframework.data.repository.query.Param;


public interface PostRepo extends JpaRepository<Post, Integer> {

     List<Post> findByUser(User user);
     List<Post> findByCategory(Category category);
     @Query("select p from Post p where p.title like :key ")
     List<Post> searchByTitle(@Param("key") String title);


}
