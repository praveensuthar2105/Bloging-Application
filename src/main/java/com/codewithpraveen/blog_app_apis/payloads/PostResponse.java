package com.codewithpraveen.blog_app_apis.payloads;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class PostResponse {

    private List<PostDto> content;
    private int pageNumber;
    private int pageSize;
    private Long totalElements;
    private int totalPages;
    private boolean lastPage;



}

