package com.zkrallah.students_api.student;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.time.LocalDate;
import java.util.List;


import static java.time.Month.APRIL;
import static java.time.Month.FEBRUARY;

@Configuration
public class StudentConfig {

//    @Bean
//    CommandLineRunner commandLineRunner(StudentRepository repository) {
//        return args -> {
//            Student student1 = new Student(
//                    "Zkrallah",
//                    "zkrallah@gmail.com",
//                    LocalDate.of(2004, FEBRUARY, 22)
//            );
//            Student student2 = new Student(
//                    "Zkr",
//                    "zkr@gmail.com",
//                    LocalDate.of(2002, APRIL, 23)
//            );
//
//            repository.saveAll(List.of(student1, student2));
//        };
//    }
}
