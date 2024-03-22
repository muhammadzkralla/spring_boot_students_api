package com.zkrallah.students_api.student;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

@Service
public class StudentService {

    public List<Student> getStudents() {
        return List.of(
                new Student(
                        1L,
                        "Zkrallah",
                        "zkrallah@gmail.com",
                        LocalDate.of(2004, Month.FEBRUARY, 22),
                        20
                )
        );
    }
}
