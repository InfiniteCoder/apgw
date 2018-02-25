package com.example.apgw.controller;

import com.example.apgw.model.Student;
import com.example.apgw.model.Subject;
import com.example.apgw.service.StudentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.security.Principal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.MockitoAnnotations.initMocks;

class StudentControllerTest {

    private StudentController subject;
    @Mock
    private StudentService service;
    @Mock
    private Principal principal;

    @BeforeEach
    void setUp() {
        initMocks(this);
        subject = new StudentController(service);
    }

    @Test
    void createStudent() {
        ResponseEntity<Student> response = subject.createStudent(principal);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    void getSubjects() {
        List<Subject> response = subject.getSubjects(principal);
        assertEquals(true, response.isEmpty());
    }
}