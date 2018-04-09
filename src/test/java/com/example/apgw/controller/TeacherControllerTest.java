package com.example.apgw.controller;

import com.example.apgw.model.Subject;
import com.example.apgw.model.Teacher;
import com.example.apgw.service.TeacherService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.MockitoAnnotations.initMocks;

class TeacherControllerTest {

    private TeacherController testSubject;
    @Mock
    private TeacherService service;

    @BeforeEach
    void setUp() {
        initMocks(this);
        testSubject = new TeacherController(service);
    }

    @Test
    void createTeacher() {
        Teacher teacher = new Teacher("foo@example.com", "John Doe");
        given(service.createTeacher()).willReturn(teacher);

        ResponseEntity<Teacher> reply = testSubject.createTeacher();

        assertEquals(HttpStatus.CREATED, reply.getStatusCode());
        assertEquals(teacher.getEmail(), reply.getBody().getEmail());
        assertEquals(teacher.getName(), reply.getBody().getName());
    }

    @Test
    void getSubjects() {
        List<Subject> list = new ArrayList<>();

        given(service.getSubjects()).willReturn(list);

        ResponseEntity<List<Subject>> reply = testSubject.getSubjects();

        assertEquals(HttpStatus.OK, reply.getStatusCode());
    }
}