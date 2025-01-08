

 package com.codewithpraveen.blog_app_apis.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
class SecurityConfig {

    @Bean
   public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

            http.csrf( Customizer -> Customizer.disable());
            http.authorizeHttpRequests(request -> request.anyRequest().authenticated());
            // http.formLogin(Customizer.withDefaults());
            http.httpBasic(Customizer.withDefaults());
            http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

    return http.build();
    
   }
    
}