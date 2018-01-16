package com.example.apgw.service;

import com.example.apgw.model.Student;

public class StudentService {
    private UserPrincipal userPrincipal;

    /**
     * StudentService constructor. Used to create user from userPrincipal
     *
     * @param userPrincipal UserPrincipal object, created from Principal.
     * @see java.security.Principal
     */
    public StudentService(UserPrincipal userPrincipal) {
        this.userPrincipal = userPrincipal;
    }

    /**
     * Create new Student. Pass the userPrincipal object to constructor
     *
     * @return Student object
     * @see UserPrincipal
     */
    public Student createStudent() {
        String email = userPrincipal.getEmail();
        String name = userPrincipal.getName();
        return new Student(email, name);
    }
}
