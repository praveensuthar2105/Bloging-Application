package com.codewithpraveen.blog_app_apis;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;

import com.codewithpraveen.blog_app_apis.Entites.Role;
import com.codewithpraveen.blog_app_apis.config.AppConstant;
import com.codewithpraveen.blog_app_apis.repository.RoleRepo;

@SpringBootApplication
public class BlogAppApisApplication implements CommandLineRunner {

	@Autowired
	private RoleRepo roleRepo;
	public static void main(String[] args) {
		SpringApplication.run(BlogAppApisApplication.class, args);
	}
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	@Bean
    public MultipartResolver multipartResolver() {
	return new StandardServletMultipartResolver();
}
	@Override
	public void run(String... args) throws Exception {
		try {
			Role role = new Role();
			role.setId(AppConstant.ADMIN_USER);
			role.setName("Admin_User");

			Role role1 = new Role();
			role1.setId(AppConstant.NORMAL_USER);
			role1.setName("Normal_User");

			List<Role> roles = List.of(role, role1);
			List<Role> result = this.roleRepo.saveAll(roles);

			result.forEach(r -> System.out.println(r.getName()));
		} catch(Exception e) {
			e.printStackTrace();

		}
	}


}