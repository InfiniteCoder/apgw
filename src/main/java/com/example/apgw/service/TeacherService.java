package com.example.apgw.service;
import com.example.apgw.model.Teacher;


public class TeacherService {
    private UserPrincipal userPrincipal;

    public TeacherService(UserPrincipal userPrincipal) {
        this.userPrincipal = userPrincipal;
    }

    public Teacher createTeacher() {
        String email = userPrincipal.getEmail();
        String name = userPrincipal.getName();
        return new Teacher(email, name);
    }
}
