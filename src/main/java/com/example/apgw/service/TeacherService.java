package com.example.apgw.service;

import com.example.apgw.model.Teacher;

import java.security.Principal;

public class TeacherService {
    private Principal principal;

    public TeacherService(Principal principal) {
        this.principal = principal;
    }

    public Teacher createTeacher() {
        UserInfo userInfo = new UserInfo(principal);
        String email = userInfo.userEmail();
        String name = userInfo.userName();
        return new Teacher(email, name);
    }
}
