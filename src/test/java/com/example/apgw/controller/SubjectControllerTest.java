package com.example.apgw.controller;

import com.example.apgw.service.SubjectService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.security.acl.NotOwnerException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
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
        given(service.addSubject("Foo", "I", "CSE")).willReturn(expected);

        ResponseEntity<String> reply = testSubject.addSubject("Foo", "I", "CSE");

        assertEquals(HttpStatus.CREATED, reply.getStatusCode());
    }

    @Test
    void addSubjectShouldReturnNotModified() {
        String expected = "Subject already exists";
        given(service.addSubject("Foo", "I", "CSE")).willReturn(expected);

        ResponseEntity<String> reply = testSubject.addSubject("Foo", "I", "CSE");

        assertEquals(HttpStatus.NOT_MODIFIED, reply.getStatusCode());
    }

    @Test
    void addSubjectShouldReturnNoContent() {
        String expected = "Name can not be empty";
        given(service.addSubject("Foo", "I", "CSE")).willReturn(expected);

        ResponseEntity<String> reply = testSubject.addSubject("Foo", "I", "CSE");

        assertEquals(HttpStatus.NO_CONTENT, reply.getStatusCode());
    }

    @Test
    void deleteSubjectShouldReturnOK() throws NotOwnerException {
        long id = 1;
        doNothing().when(service).deleteSubject(id);
        ResponseEntity<String> reply = testSubject.deleteSubject(id);

        assertEquals(HttpStatus.OK, reply.getStatusCode());
    }

    @Test
    void deleteSubjectShouldReturnNotFound() throws NotOwnerException {
        long id = 1;
        doThrow(new NotOwnerException()).when(service).deleteSubject(id);
        ResponseEntity<String> reply = testSubject.deleteSubject(id);

        assertEquals(HttpStatus.NOT_FOUND, reply.getStatusCode());
    }
}