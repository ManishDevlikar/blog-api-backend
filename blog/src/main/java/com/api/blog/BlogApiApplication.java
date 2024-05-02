package com.api.blog;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.api.blog.config.AppConstans;
import com.api.blog.entities.Role;
import com.api.blog.repositories.Role_Repo;

@SpringBootApplication
public class BlogApiApplication implements CommandLineRunner {

	@Autowired
	private Role_Repo role_Repo;

	public static void main(String[] args) {
		SpringApplication.run(BlogApiApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	@Override
	public void run(String... args) throws Exception {
		try {
			Role role1 = new Role();
			role1.setId(AppConstans.ADMIN_USER);
			role1.setName("ROLE_ADMIN");

			Role role2 = new Role();
			role2.setId(AppConstans.NORMAL_USER);
			role2.setName("ROLE_NORMAL");

			List<Role> roleList = List.of(role1, role2);

			List<Role> savedRole = this.role_Repo.saveAll(roleList);
			savedRole.stream().forEach(System.out::println);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
