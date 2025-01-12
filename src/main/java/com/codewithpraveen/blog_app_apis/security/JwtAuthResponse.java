package com.codewithpraveen.blog_app_apis.security;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class JwtAuthResponse {

    private String token;
}
