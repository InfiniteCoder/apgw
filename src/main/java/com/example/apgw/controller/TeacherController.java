package com.example.apgw.controller;

import com.example.apgw.model.Teacher;
import com.example.apgw.repository.TeacherRepository;
import com.example.apgw.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class TeacherController {

    private final TeacherRepository teacherRepository;

    @Autowired
    public TeacherController(TeacherRepository teacherRepository) {
        this.teacherRepository = teacherRepository;
    }

    @PostMapping("/createTeacher")
    @ResponseBody
    public ResponseEntity<Teacher> createTeacher(Principal principal) {
        Teacher teacher = new TeacherService(principal).createTeacher();
        teacherRepository.save(teacher);
        return new ResponseEntity<>(teacher, HttpStatus.CREATED);
    }
}
