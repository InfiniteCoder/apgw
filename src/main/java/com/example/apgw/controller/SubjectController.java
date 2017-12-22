package com.example.apgw.controller;

import com.example.apgw.model.Subject;
import com.example.apgw.model.Teacher;
import com.example.apgw.repository.SubjectRepository;
import com.example.apgw.repository.TeacherRepository;
import com.example.apgw.service.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class SubjectController {

    @Autowired
    SubjectRepository subjectRepository;
    @Autowired
    TeacherRepository teacherRepository;

    @PostMapping("/addSubject")
    public void addSubject(Principal principal,
                           @RequestParam(name = "name") String name) {
        UserPrincipal userPrincipal = new UserPrincipal(principal);
        Teacher teacher = teacherRepository.findOne(userPrincipal.getEmail());
        Subject subject = new Subject(name, teacher);
        subjectRepository.save(subject);
    }
}
