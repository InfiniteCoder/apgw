package com.example.apgw.controller;

import com.example.apgw.service.SubjectService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.MockitoAnnotations.initMocks;

class SubjectControllerTest {

    @Mock
    private SubjectService service;
    private SubjectController testSubject;

    @BeforeEach
    void setUp() {
        initMocks(this);
        testSubject = new SubjectController(service);
    }

    @Test
    void addSubjectShouldReturnCreated() {
        String expected = "Subject added";
        given(service.addSubject("Foo")).willReturn(expected);

        ResponseEntity<String> reply = testSubject.addSubject("Foo");

        assertEquals(HttpStatus.CREATED, reply.getStatusCode());
    }

    @Test
    void addSubjectShouldReturnNotModified() {
        String expected = "Subject already exists";
        given(service.addSubject("Foo")).willReturn(expected);

        ResponseEntity<String> reply = testSubject.addSubject("Foo");

        assertEquals(HttpStatus.NOT_MODIFIED, reply.getStatusCode());
    }

    @Test
    void addSubjectShouldReturnNoContent() {
        String expected = "Name can not be empty";
        given(service.addSubject("Foo")).willReturn(expected);

        ResponseEntity<String> reply = testSubject.addSubject("Foo");

        assertEquals(HttpStatus.NO_CONTENT, reply.getStatusCode());
    }
}