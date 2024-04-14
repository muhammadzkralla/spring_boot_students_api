package com.zkrallah.students_api.controller;

import com.zkrallah.students_api.entity.Request;
import com.zkrallah.students_api.entity.Student;
import com.zkrallah.students_api.response.MessageResponse;
import com.zkrallah.students_api.service.StudentService;
import com.zkrallah.students_api.service.request.RequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/students")
public class StudentController {

    private final StudentService studentService;
    private final RequestService requestService;

    @GetMapping
    public List<Student> getStudents() {
        return studentService.getStudents();
    }

    @PostMapping
    public void registerStudent(@RequestBody Student student) {
        studentService.addStudent(student);
    }

    @DeleteMapping(path = "{studentId}")
    public void deleteStudent(@PathVariable("studentId") Long studentId) {
        studentService.deleteStudent(studentId);
    }

    @PutMapping(path = "{studentId}")
    public void updateStudent(
            @PathVariable("studentId") Long studentId,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String email
    ) {
        studentService.updateStudent(studentId, name, email);
    }

    @PostMapping("/request/{userId}/to/{classId}")
    public ResponseEntity<?> requestUserToClass(@PathVariable Long userId, @PathVariable Long classId) {
        try {
            Request request = requestService.createRequest(userId, classId);
            return ResponseEntity.ok(request);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new MessageResponse("Could not create request: " + e.getMessage()));
        }
    }
}
