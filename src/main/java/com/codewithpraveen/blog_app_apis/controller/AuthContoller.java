package com.codewithpraveen.blog_app_apis.controller;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;

import com.codewithpraveen.blog_app_apis.payloads.JwtAuthRequest;
import com.codewithpraveen.blog_app_apis.payloads.JwtResponse;
import com.codewithpraveen.blog_app_apis.payloads.UserDto;
import com.codewithpraveen.blog_app_apis.security.JwtUtils;
import com.codewithpraveen.blog_app_apis.security.UserDetailsServiceImpl;
import com.codewithpraveen.blog_app_apis.service.UserService;

@RestController
@RequestMapping("/api/users")
public class AuthContoller {


    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private AuthenticationManager Manager;

    @Autowired
    private UserService userService;



    private Logger logger = Logger.getLogger(AuthContoller.class.getName());

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody JwtAuthRequest request) {
       
        doAuthenticate(request.getUsername(), request.getPassword());
        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());
        String jwt = JwtUtils.generateToken(userDetails.getUsername());
        JwtResponse jwt1 = new JwtResponse(jwt);
        return new ResponseEntity<JwtResponse>(jwt1, HttpStatus.OK);
      
        
       
    }

    private void doAuthenticate(String username, String password) {
       UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password);
       try {
       this.Manager.authenticate(token);
       } catch (BadCredentialsException e) {
        throw new BadCredentialsException("Invalid username or password");
       }

    }
    // @ExceptionHandler(BadCredentialsException.class)
    // public String exceptionHandler() {
    //     return "Invalid username or password";
    // }
    @PostMapping("/register")
    public ResponseEntity<UserDto> registerUser(@RequestBody UserDto userDto) {
       UserDto user = this.userService.registerNewUser(userDto);
    
       return new ResponseEntity<UserDto>(user, HttpStatus.CREATED);
    }

}
