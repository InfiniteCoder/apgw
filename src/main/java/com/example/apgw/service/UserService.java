package com.example.apgw.service;

import com.example.apgw.model.Student;
import com.example.apgw.model.Teacher;
import com.example.apgw.repository.StudentRepository;
import com.example.apgw.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
public class UserService {
    @Autowired
    StudentRepository studentRepository;
    @Autowired
    TeacherRepository teacherRepository;

    public String getType(Principal principal) {
        String email = getEmail(principal);

        //check if student
        Student student = studentRepository.findOne(email);
        if (student != null) {
            return "student";
        }
        Teacher teacher = teacherRepository.findOne(email);
        if (teacher != null) {
            return "teacher";
        }
        return "new";
    }

    public String getEmail(Principal principal) {
        UserPrincipal userPrincipal = new UserPrincipal(principal);
        return userPrincipal.getEmail();
    }

    public String getName(Principal principal) {
        UserPrincipal userPrincipal = new UserPrincipal(principal);
        return userPrincipal.getName();
    }

    public String getPicture(Principal principal) {
        UserPrincipal userPrincipal = new UserPrincipal(principal);
        return userPrincipal.getPicture();
    }
}
