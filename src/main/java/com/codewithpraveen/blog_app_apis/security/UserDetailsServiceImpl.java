package com.codewithpraveen.blog_app_apis.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.codewithpraveen.blog_app_apis.Entites.User;
import com.codewithpraveen.blog_app_apis.exceptions.ResourceNotFoundException;
import com.codewithpraveen.blog_app_apis.repository.UserRepo;


@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       User user = userRepo.findByEmail(username).orElseThrow(() -> new ResourceNotFoundException(username, username, username));
        if(user != null ) {
            return org.springframework.security.core.userdetails.User.builder()
            .username(user.getEmail())
            .password(user.getPassword())
            .roles(user.getRoles().stream().map(role -> role.getName()).toArray( String[] :: new))
            .build();
        }
        throw new UsernameNotFoundException("User not found with username: " + username);
    }
    //  public void configure(AuthenticationManagerBuilder auth) throws Exception {
    //     auth.userDetailsService(this).passwordEncoder(passwordEncoder());
    // }
    // //  @Bean
    // public PasswordEncoder passwordEncoder() {
    //     return new BCryptPasswordEncoder();
    // }

}
