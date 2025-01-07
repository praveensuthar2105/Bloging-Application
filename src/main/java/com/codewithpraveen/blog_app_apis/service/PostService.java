package com.codewithpraveen.blog_app_apis.service;

import java.util.List;

import com.codewithpraveen.blog_app_apis.payloads.PostDto;
import com.codewithpraveen.blog_app_apis.payloads.PostResponse;

public interface PostService {

    PostDto createPost(PostDto postDto , Integer userId , Integer categoryId);
    PostDto updatePost(PostDto postDto , Integer postId);
    void deletePost(Integer postId);
    
    PostResponse getAllPosts(Integer pageNumber , Integer pageSize , String sortBy , String sortOrder);
    List<PostDto> getPostsByCategory(Integer categoryId);
    List<PostDto> getPostsByUser(Integer userId);
    PostDto getPostById( Integer postId);
    List<PostDto> searchPosts(String keyword);

}
