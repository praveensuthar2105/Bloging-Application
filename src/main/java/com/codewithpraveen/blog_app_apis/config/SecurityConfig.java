
package com.codewithpraveen.blog_app_apis.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.SecurityFilterChain;

import com.codewithpraveen.blog_app_apis.security.CustomUserDetailService;
import com.codewithpraveen.blog_app_apis.security.JwtAuthenticationEntryPoint;
import com.codewithpraveen.blog_app_apis.security.JwtFilter;

@Configuration
@EnableWebSecurity
class SecurityConfig {

     @Autowired
     private CustomUserDetailService customUserDetailService;

     @Autowired
     private JwtFilter jwtFilter;

     @Autowired
     private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

     @Bean
     public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

          http.csrf(Customizer -> Customizer.disable());
          http.authorizeHttpRequests(request -> request.anyRequest().authenticated());
          http.formLogin(Customizer.withDefaults());
          // http.httpBasic(Customizer.withDefaults());
          http.exceptionHandling(Customizer -> Customizer.authenticationEntryPoint(jwtAuthenticationEntryPoint));
          http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
          http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

          return http.build();

     }

     // @Bean
     // public AuthenticationProvider authenticationProvider(CustomUserDetailService
     // customUserDetailService, PasswordEncoder passwordEncoder) throws Exception {

     // DaoAuthenticationProvider authenticationProvider = new
     // DaoAuthenticationProvider();

     // authenticationProvider.setUserDetailsService(customUserDetailService);
     // authenticationProvider.setPasswordEncoder(passwordEncoder);

     // return authenticationProvider;

     // }
     @Bean
     public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
               throws Exception {
          return authenticationConfiguration.getAuthenticationManager();
     }

     public void configure(AuthenticationManagerBuilder auth) throws Exception {
          auth.userDetailsService(customUserDetailService).passwordEncoder(passwordEncoder());

     }

     @Bean
     public PasswordEncoder passwordEncoder() {
          return new BCryptPasswordEncoder();
     }

}