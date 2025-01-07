package com.codewithpraveen.blog_app_apis.payloads;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostDto {
    private Integer postId;
    private String title;
    private String content;
   private String imageName;

   private Date addedDate;
    
   private CategoryDto category;
   private UserDto user;
   


}
// Compare this snippet from src/main/java/com/codewithpraveen/blog_app_apis/Entites/User.java: