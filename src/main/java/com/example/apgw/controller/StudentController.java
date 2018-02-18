package com.example.apgw.controller;

import com.example.apgw.model.Student;
import com.example.apgw.model.StudentSubject;
import com.example.apgw.model.Subject;
import com.example.apgw.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
public class StudentController {

    private final StudentService service;

    @Autowired
    public StudentController(StudentService service) {
        this.service = service;
    }

    /**
     * endpoint to create new student.
     *
     * @param principal Provided by Spring
     * @return Created Student
     * @see Student
     */
    @PostMapping("/createStudent")
    @ResponseBody
    public ResponseEntity<Student> createStudent(Principal principal) {
        Student student = service.createStudent(principal);
        return new ResponseEntity<>(student, HttpStatus.CREATED);
    }

    /**
     * endpoint to get list of subjects of students.
     *
     * @param principal Provided by Spring
     * @return List of StudentSubject class
     * @see StudentSubject
     */
    @GetMapping("/student/subjects")
    @ResponseBody
    public List<Subject> getSubjects(Principal principal) {
        return service.getSubjects(principal);
    }
}
