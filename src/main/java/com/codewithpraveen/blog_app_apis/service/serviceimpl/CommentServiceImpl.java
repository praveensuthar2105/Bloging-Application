package com.codewithpraveen.blog_app_apis.service.serviceimpl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codewithpraveen.blog_app_apis.Entites.Comment;
import com.codewithpraveen.blog_app_apis.Entites.Post;
import com.codewithpraveen.blog_app_apis.exceptions.ResourceNotFoundException;
import com.codewithpraveen.blog_app_apis.payloads.CommentDto;
import com.codewithpraveen.blog_app_apis.repository.CommentRepo;
import com.codewithpraveen.blog_app_apis.repository.PostRepo;
import com.codewithpraveen.blog_app_apis.service.CommentService;

@Service
public class CommentServiceImpl  implements CommentService{

    @Autowired
    private CommentRepo commentRepo;

    @Autowired
    private PostRepo postRepo;
    
    @Autowired
    private ModelMapper modelmapper;

    @Override
    public CommentDto createComment(CommentDto commentDto, Integer postid) {
      
        Post post = postRepo.findById(postid).orElseThrow(()-> new ResourceNotFoundException("Post", "post Id",   postid));

        Comment comment = modelmapper.map(commentDto, Comment.class);

        comment.setPost(post);
        Comment savedComment = this.commentRepo.save(comment);

        return this.modelmapper.map(savedComment, CommentDto.class);
    }

    @Override
    public void deleteComment(Integer commentId) {
      
        Comment com = this.commentRepo.findById(commentId).orElseThrow(()-> new ResourceNotFoundException("Comment", "comment Id",   commentId));
        this.commentRepo.delete(com);

    }

}
