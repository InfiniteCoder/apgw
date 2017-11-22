package com.example.apgw.controller;

import com.example.apgw.model.Teacher;
import com.example.apgw.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
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

    @RequestMapping("/createTeacher")
    @ResponseBody
    public Teacher createTeacher(Principal principal) {
        UserInfo userInfo = new UserInfo();
        String email = userInfo.userEmail(principal);
        String name = userInfo.userName(principal);

        //create Teacher
        Teacher teacher = new Teacher(email, name);
        teacherRepository.save(teacher);
        return teacher;
    }
}
