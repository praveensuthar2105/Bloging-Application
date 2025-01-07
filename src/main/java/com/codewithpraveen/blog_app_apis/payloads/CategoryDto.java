package com.codewithpraveen.blog_app_apis.payloads;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class CategoryDto {

    private Integer categoryId;
    @NotBlank
    @Size(min =  4 , message = "Category title should be atleast 4 characters long")
    private String categoryTitle;

    @NotBlank
    @Size(min = 10 , message = "Category description should be atleast 10 characters long")
    private String categoryDescription;


}
