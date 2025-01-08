package com.codewithpraveen.blog_app_apis.service;

import com.codewithpraveen.blog_app_apis.payloads.CommentDto;

public interface CommentService {


    CommentDto createComment(CommentDto commentDto , Integer postid);

    void deleteComment(Integer commentId);
}
