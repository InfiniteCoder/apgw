package com.example.apgw.controller;

import com.example.apgw.model.Student;
import com.example.apgw.model.Teacher;
import com.example.apgw.model.User;
import com.example.apgw.repository.StudentRepository;
import com.example.apgw.repository.TeacherRepository;
import com.example.apgw.service.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class UserInfoController {

    private final StudentRepository studentRepository;

    @GetMapping(value = "/user")
    public ResponseEntity<User> getUser(Principal principal) {
        return new ResponseEntity<>(new UserInfo(principal).getUser(), HttpStatus.OK);
    }

    private final TeacherRepository teacherRepository;

    @Autowired
    public UserInfoController(StudentRepository studentRepository, TeacherRepository teacherRepository) {
        this.studentRepository = studentRepository;
        this.teacherRepository = teacherRepository;
    }

    @GetMapping(value = "/all/isUserAuth")
    public boolean isAuth(Authentication authentication) {
        return authentication != null && authentication.isAuthenticated();
    }

    @GetMapping(value = "/userType")
    public ResponseEntity<String> getUserType(Principal principal) {
        String email = new UserInfo(principal).userEmail();
        //check if a student
        Student student = studentRepository.findOne(email);
        if (student != null) {
            return new ResponseEntity<>("student", HttpStatus.OK);
        }
        //check if teacher
        Teacher teacher = teacherRepository.findOne(email);
        if (teacher != null) {
            return new ResponseEntity<>("teacher", HttpStatus.OK);
        }
        //else, new user
        return new ResponseEntity<>("new", HttpStatus.OK);
    }
}
