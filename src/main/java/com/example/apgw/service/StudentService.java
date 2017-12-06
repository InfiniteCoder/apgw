package com.example.apgw.service;

import com.example.apgw.model.Student;

import java.security.Principal;

public class StudentService {
    private Principal principal;

    public StudentService(Principal principal) {
        this.principal = principal;
    }

    public Student createStudent(int rollNo) {
        UserInfo userInfo = new UserInfo(principal);
        String email = userInfo.userEmail();
        String name = userInfo.userName();
        return new Student(email, name, rollNo);
    }
}
