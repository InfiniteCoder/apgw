package com.example.apgw.controller;

import com.example.apgw.model.Student;
import com.example.apgw.repository.StudentRepository;
import com.example.apgw.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;


@RestController
public class StudentController {

    private final StudentRepository studentRepository;

    @Autowired
    public StudentController(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    //create Student
    @PostMapping("/createStudent")
    @ResponseBody
    public ResponseEntity<Student> createStudent(Principal principal, @RequestParam(name = "rollNo", defaultValue = "0") int rollNo) {
        Student student = new StudentService(principal).createStudent(rollNo);
        studentRepository.save(student);
        return new ResponseEntity<>(student, HttpStatus.CREATED);
    }
}
