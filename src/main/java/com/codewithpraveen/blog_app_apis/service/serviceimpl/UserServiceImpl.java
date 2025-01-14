package com.codewithpraveen.blog_app_apis.service.serviceimpl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.codewithpraveen.blog_app_apis.Entites.Role;
import com.codewithpraveen.blog_app_apis.Entites.User;
import com.codewithpraveen.blog_app_apis.config.AppConstant;
import com.codewithpraveen.blog_app_apis.payloads.UserDto;
import com.codewithpraveen.blog_app_apis.repository.RoleRepo;
import com.codewithpraveen.blog_app_apis.repository.UserRepo;
import com.codewithpraveen.blog_app_apis.service.UserService;

import com.codewithpraveen.blog_app_apis.exceptions.ResourceNotFoundException;
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepo userRepo;
   
   
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepo roleRepo;

    @Override
    public UserDto createUser(UserDto userDto) {
        User user = this.dtoToUser(userDto);
        User savedUser = this.userRepo.save(user);
        return this.userToDto(savedUser);

    }

    @Override
    public UserDto getUserById(int id) {
       User user = this.userRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
        return this.userToDto(user);
    }

    @Override
    public UserDto updateUser(UserDto userDto, Integer userId) {
        //
      User user = this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
      
         user.setName(userDto.getName());
         user.setEmail(userDto.getEmail());
         user.setPassword(userDto.getPassword());
         user.setAbout(userDto.getAbout());

            User updatedUser = this.userRepo.save(user);
            UserDto userdto1= this.userToDto(updatedUser);
            return userdto1;


    }

    @Override
    public void deleteUser(int id) {
        User user = this.userRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
        this.userRepo.delete(user);

    }

    @Override
    public List<UserDto> getAllUsers() {
       List<User> users = this.userRepo.findAll();
       List<UserDto> userDto = users.stream().map(user -> this.userToDto(user)).collect(Collectors.toList());

        return userDto;
    }

    private User dtoToUser(UserDto userDto) {
        User user = this.modelMapper.map(userDto, User.class);

        // user.setId(userDto.getId());
        // user.setName(userDto.getName());
        // user.setEmail(userDto.getEmail());
        // user.setPassword(userDto.getPassword());
        // user.setAbout(userDto.getAbout());
        
        return user;
    }

    public UserDto userToDto(User user) {
        UserDto userDto = this.modelMapper.map(user, UserDto.class);
        // userDto.setId(user.getId());
        // userDto.setName(user.getName());
        // userDto.setEmail(user.getEmail());
        // userDto.setPassword(user.getPassword());
        // userDto.setAbout(user.getAbout());
        
        return userDto;
    }

    @Override
    public UserDto registerNewUser(UserDto user) {
       User user1 = this.modelMapper.map(user , User.class);

       user1.setPassword(this.passwordEncoder.encode(user.getPassword()));

       Role role = this.roleRepo.findById(AppConstant.NORMAL_USER).get();

       user1.getRoles().add(role);
       User newUser = this.userRepo.save(user1);
       return this.modelMapper.map(newUser, UserDto.class);

    }

}
