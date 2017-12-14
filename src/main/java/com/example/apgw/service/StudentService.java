package com.example.apgw.service;

import com.example.apgw.model.Student;

public class StudentService {
    private UserPrincipal userPrincipal;

    public StudentService(UserPrincipal userPrincipal) {
        this.userPrincipal = userPrincipal;
    }

    public Student createStudent(int rollNo) {
        String email = userPrincipal.getEmail();
        String name = userPrincipal.getName();
        return new Student(email, name, rollNo);
    }
}
