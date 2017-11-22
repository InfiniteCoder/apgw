package com.example.apgw.controller;

import com.example.apgw.model.Student;
import com.example.apgw.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
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
    @RequestMapping("/createStudent")
    @ResponseBody
    public Student createStudent(Principal principal, @RequestParam(name = "rollNo", defaultValue = "0") int rollNo) {
        //get details from principal
        UserInfo userInfo = new UserInfo();
        String email = userInfo.userEmail(principal);
        String name = userInfo.userName(principal);

        //create Student
        Student student = new Student(email, name, rollNo);
        studentRepository.save(student);

        return student;
    }
}
