package com.codewithpraveen.blog_app_apis.controller;

import java.io.IOException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;

import com.codewithpraveen.blog_app_apis.config.AppConstant;
import com.codewithpraveen.blog_app_apis.payloads.ApiResponse;
import com.codewithpraveen.blog_app_apis.payloads.PostDto;
import com.codewithpraveen.blog_app_apis.payloads.PostResponse;
import com.codewithpraveen.blog_app_apis.service.FileService;
import com.codewithpraveen.blog_app_apis.service.PostService;



@RestController
@RequestMapping("/api/")
public class PostController {

@Autowired
private PostService postService;

@Autowired
private FileService fileService;

@Value("${project.image}")
private String path;

// Create post by user

@PostMapping("/user/{userId}/category/{categoryId}/posts")
 public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto , @PathVariable Integer userId , @PathVariable Integer categoryId ) {
    PostDto createPost = this.postService.createPost(postDto, userId, categoryId);
    return new ResponseEntity<PostDto>(createPost, HttpStatus.CREATED);
 }


//  get all posts by User
 @GetMapping("/user/{userId}/posts")
public ResponseEntity<List<PostDto>> getPostsByUser(@PathVariable Integer userId) {
   List<PostDto> posts = this.postService.getPostsByUser(userId);

   return new ResponseEntity<List<PostDto>>(posts, HttpStatus.OK);

}

// get all posts by category
@GetMapping("/{categoryId}/posts")
public ResponseEntity<List<PostDto>> getPostsByCategory(@PathVariable Integer categoryId) {
   List<PostDto> posts = this.postService.getPostsByCategory(categoryId);

   return new ResponseEntity<List<PostDto>>(posts, HttpStatus.OK);

}

// get all posts
@GetMapping("/posts")
public ResponseEntity<PostResponse> getAllPosts( @RequestParam( value = "pageNumber" , defaultValue = AppConstant.PAGE_NUMBER , required = false) Integer pageNumber, @RequestParam( value = "pageSize" , defaultValue = AppConstant.PAGE_SIZE , required = false) Integer pageSize ,
@RequestParam( value = "sortBy" , defaultValue = AppConstant.SORT_BY , required = false) String sortBy,
 @RequestParam( value = "sortOrder" , defaultValue = AppConstant.SORT_ORDER , required = false) String sortOrder) {
   PostResponse postResponse = this.postService.getAllPosts( pageNumber , pageSize , sortBy , sortOrder);

   return new ResponseEntity<PostResponse>(postResponse, HttpStatus.OK);
}

// get post by id

@GetMapping("/posts/{postId}")
public ResponseEntity<PostDto> getPostById(@PathVariable Integer postId) {

   PostDto post = this.postService.getPostById(postId);
   return new ResponseEntity<PostDto>(post, HttpStatus.OK);

}

// delete post
@DeleteMapping("/posts/{postId}")
public ApiResponse deletePost(@PathVariable Integer PostId) {
   this.postService.deletePost(PostId);
   return new ApiResponse( "Post deleted successfully" , true);
}

// update post
@PutMapping("/posts/{postId}")
public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto , @PathVariable Integer postId) {
   PostDto updatedPost = this.postService.updatePost(postDto, postId);
   return new ResponseEntity<PostDto>(updatedPost, HttpStatus.OK);

}
//search by title

   @GetMapping("/posts/search/{keywords}")
public ResponseEntity<List<PostDto>> searchPosts(@PathVariable("keywords") String keywords ) {
   List<PostDto> posts = this.postService.searchPosts(keywords);
   return new ResponseEntity<List<PostDto>>(posts, HttpStatus.OK);
}

// post image upload
  

@PostMapping("/post/file/upload/{postId}")
  public ResponseEntity<PostDto> uploadPostImage(@RequestParam("file") MultipartFile file ,
  @PathVariable Integer postId) throws IOException {
       PostDto postDto = this.postService.getPostById(postId);
      String fileName = this.fileService.uploadImage(path, file);
    
      postDto.setImageName(fileName);
      this.postService.updatePost(postDto, postId);

      return new ResponseEntity<PostDto>(postDto , HttpStatus.OK);
  }

}
