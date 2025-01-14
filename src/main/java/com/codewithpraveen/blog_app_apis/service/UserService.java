package com.codewithpraveen.blog_app_apis.service;

import java.util.List;

import com.codewithpraveen.blog_app_apis.payloads.UserDto;

public interface UserService {
    UserDto registerNewUser(UserDto user);
    UserDto createUser(UserDto user);
    UserDto getUserById(int id);
    UserDto updateUser(UserDto user,Integer id);
    void deleteUser(int id);
    List<UserDto> getAllUsers();
   

}
