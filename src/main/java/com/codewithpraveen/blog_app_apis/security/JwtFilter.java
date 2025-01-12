package com.codewithpraveen.blog_app_apis.security;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private CustomUserDetailService customUserDetailService;
    
    
   @Autowired
    private JwtTokenHelper jwtTokenHelper;

   

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)  throws ServletException, IOException {
          
        String requestToken = request.getHeader("Authorization");
        String username = null;
        String jwt = null;

        if(requestToken != null && requestToken.startsWith("Bearer ")) {
            jwt = requestToken.substring(7);
           try{
            username = JwtTokenHelper.getUsernameFromToken(requestToken);
           } catch (IllegalArgumentException e) {
                System.out.println("Unable to get JWT Token");
              } catch ( ExpiredJwtException e) {
                System.out.println("JWT Token has expired");
           } catch ( MalformedJwtException e) {
                System.out.println("JWT Token is invalid");
           }

        } else {
            System.out.println("JWT Token does not begin with Bearer String");
        }
        if(username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
           
            UserDetails userDetails = customUserDetailService.loadUserByUsername(username);
          
            if(jwtTokenHelper.validateToken(jwt , userDetails.getUsername())) {
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            } else {
                System.out.println("Token is not valid");
            }
        }  else {
            System.out.println("username is null or Security Context is not null");
        }

      filterChain.doFilter(request, response);

    }
      
    }


