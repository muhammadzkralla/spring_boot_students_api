package com.zkrallah.students_api;

import com.zkrallah.students_api.entity.Role;
import com.zkrallah.students_api.service.MailSenderService;
import com.zkrallah.students_api.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;

@SpringBootApplication
public class StudentsApiApplication {
	@Autowired
	private MailSenderService senderService;

	public static void main(String[] args) {
		SpringApplication.run(StudentsApiApplication.class, args);
	}

	@Bean
	CommandLineRunner run(UserService userService) {
		return args -> {
			Role role1 = userService.saveRole(new Role(null, "ADMIN"));
			Role role2 = userService.saveRole(new Role(null, "TEACHER"));
			Role role3 = userService.saveRole(new Role(null, "STUDENT"));
		};
	}

//	@EventListener(ApplicationReadyEvent.class)
//	private void sendMail() {
//		senderService.sendEmail(
//				"muhammad.hesham442@gmail.com",
//				"Testing Spring Java Mail Sender",
//				"This is Just a Test."
//		);
//	}

}
