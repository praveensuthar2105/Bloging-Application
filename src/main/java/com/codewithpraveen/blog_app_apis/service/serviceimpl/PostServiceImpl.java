package com.codewithpraveen.blog_app_apis.service.serviceimpl;

import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.codewithpraveen.blog_app_apis.Entites.Category;
import com.codewithpraveen.blog_app_apis.Entites.Post;
import com.codewithpraveen.blog_app_apis.Entites.User;
import com.codewithpraveen.blog_app_apis.exceptions.ResourceNotFoundException;
import com.codewithpraveen.blog_app_apis.payloads.PostDto;
import com.codewithpraveen.blog_app_apis.payloads.PostResponse;
import com.codewithpraveen.blog_app_apis.repository.CategoryRepo;
import com.codewithpraveen.blog_app_apis.repository.PostRepo;
import com.codewithpraveen.blog_app_apis.repository.UserRepo;
import com.codewithpraveen.blog_app_apis.service.PostService;

@Service
public class PostServiceImpl implements PostService {
    @Autowired
    private PostRepo postRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private CategoryRepo categoryRepo;

    @Override
    public PostDto createPost(PostDto postDto , Integer userId , Integer categoryId) {
        
            User user = this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "User id", userId));
            Category category = this.categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category", "Category id", categoryId));
        Post post = modelMapper.map(postDto, Post.class);
        post.setImageName("default.jpg");
        post.setAddedDate(new Date(0));
        post.setUser(user);
        post.setCategory(category);
        Post savedPost = this.postRepo.save(post);
        return this.modelMapper.map(savedPost, PostDto.class);

    }

    @Override
    public PostDto updatePost(PostDto postDto, Integer postId) {
       Post post = this.postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "Post id", postId));
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        Post updatedPost = this.postRepo.save(post);
        return this.modelMapper.map(updatedPost, PostDto.class);
    }

    @Override
    public void deletePost(Integer postId) {
       Post posts = this.postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "Post id", postId));
        this.postRepo.delete(posts);
    }

    @Override
    public PostResponse getAllPosts(Integer pageNumber , Integer pageSize , String sortBy , String sortOrder) {
       
        Sort sort = null;

        if(sortOrder.equalsIgnoreCase("asc") ) {
            sort = Sort.by(sortBy).ascending();
        } else {
            sort = Sort.by(sortBy).descending();
        }

           Pageable  pageable = PageRequest.of(pageNumber , pageSize , sort );
            Page<Post> pagePost =  this.postRepo.findAll(pageable);
            List<Post> allPosts= pagePost.getContent();
        List<PostDto> postDtos = allPosts.stream().map((post) -> modelMapper.map(post, PostDto.class)).collect(Collectors.toList());

        PostResponse postResponse = new PostResponse();

        postResponse.setContent(postDtos);
        postResponse.setPageNumber(pagePost.getNumber());
        postResponse.setPageSize(pagePost.getSize());
        postResponse.setTotalElements(pagePost.getTotalElements());
        postResponse.setTotalPages(pagePost.getTotalPages());
        postResponse.setLastPage(pagePost.isLast());


        return postResponse;
    }

    @Override
    public List<PostDto> getPostsByCategory(Integer categoryId) {
      
        Category cat = this.categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category", "Category id", categoryId));
        List<Post> posts = this.postRepo.findByCategory(cat);
        List<PostDto> postDtos = posts.stream().map((post) -> modelMapper.map(post, PostDto.class)).collect(Collectors.toList());

        return postDtos;
    }

    @Override
    public List<PostDto> getPostsByUser(Integer userId) {
     
        User user = this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "User id", userId));
        List<Post> posts = this.postRepo.findByUser(user);
        List<PostDto> postDtos = posts.stream().map(post -> modelMapper.map(post, PostDto.class)).collect(Collectors.toList());

        return postDtos;

    }

    @Override
    public PostDto getPostById(Integer postId) {
       Post post = this.postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "Post id", postId));
        return this.modelMapper.map(post, PostDto.class);
    }

    @Override
    public List<PostDto> searchPosts(String keyword) {
        List<Post> posts = this.postRepo.searchByTitle("%"+keyword+"%");
        List<PostDto> postDtos = posts.stream().map(post -> modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
        return postDtos;
    }


}
