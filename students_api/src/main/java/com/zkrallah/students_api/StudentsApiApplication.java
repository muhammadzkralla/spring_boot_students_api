package com.zkrallah.students_api;

import com.zkrallah.students_api.entity.Role;
import com.zkrallah.students_api.service.user.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class StudentsApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(StudentsApiApplication.class, args);
	}

	@Bean
	CommandLineRunner run(UserService userService) {
		return args -> {
			Role role1 = userService.saveRole(new Role(null, "ADMIN"));
			Role role2 =userService.saveRole(new Role(null, "TEACHER"));
			Role role3 =userService.saveRole(new Role(null, "STUDENT"));
		};
	}

}
