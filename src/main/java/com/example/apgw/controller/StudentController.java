package com.example.apgw.controller;

import com.example.apgw.model.Student;
import com.example.apgw.model.Subject;
import com.example.apgw.repository.StudentRepository;
import com.example.apgw.service.StudentService;
import com.example.apgw.service.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;


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
    public ResponseEntity<Student> createStudent(Principal principal,
                                                 @RequestParam(name = "rollNo", defaultValue = "0") int rollNo) {
        UserPrincipal userPrincipal = new UserPrincipal(principal);
        Student student = new StudentService(userPrincipal).createStudent(rollNo);
        studentRepository.save(student);
        return new ResponseEntity<>(student, HttpStatus.CREATED);
    }

    //get list of subjects
    @GetMapping("/student/subjects")
    @ResponseBody
    public List<Subject> getSubjects(Principal principal) {
        UserPrincipal userPrincipal = new UserPrincipal(principal);
        Student student = studentRepository.findOne(userPrincipal.getEmail());
        return student.getSubjects();
    }
}
