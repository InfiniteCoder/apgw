package com.example.apgw.controller;

import com.example.apgw.model.Student;
import com.example.apgw.model.Teacher;
import com.example.apgw.model.User;
import com.example.apgw.repository.StudentRepository;
import com.example.apgw.repository.TeacherRepository;
import com.example.apgw.service.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserInfoController {

    private final StudentRepository studentRepository;
    private final TeacherRepository teacherRepository;

    @Autowired
    public UserInfoController(StudentRepository studentRepository, TeacherRepository teacherRepository) {
        this.studentRepository = studentRepository;
        this.teacherRepository = teacherRepository;
    }

    @GetMapping(value = "/user")
    public ResponseEntity<User> getUser(UserPrincipal userPrincipal) {
        String email = userPrincipal.getEmail();
        String name = userPrincipal.getName();
        String picture = userPrincipal.getPicture();
        User user = new User(email, picture, name);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping(value = "/all/isUserAuth")
    public boolean isAuth(Authentication authentication) {
        return authentication != null && authentication.isAuthenticated();
    }

    @GetMapping(value = "/userType")
    public ResponseEntity<String> getUserType(UserPrincipal userPrincipal) {
        String email = userPrincipal.getEmail();
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
