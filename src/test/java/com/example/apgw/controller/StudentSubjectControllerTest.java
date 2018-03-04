package com.example.apgw.controller;

import com.example.apgw.model.StudentSubject;
import com.example.apgw.service.StudentSubjectService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.MockitoAnnotations.initMocks;

class StudentSubjectControllerTest {

    @Mock
    private StudentSubjectService service;
    private StudentSubjectController subject;
    private MockMultipartFile file;

    @BeforeEach
    void setUp() {
        initMocks(this);
        subject = new StudentSubjectController(service);
        byte[] content = new byte[0];
        file = new MockMultipartFile(
                "file",
                "fileName",
                "text/plain",
                content);
    }

    @Test
    void AddSubjectWithEmptyFile() {
        String expectedReply = "Empty File";
        given(service.addStudents(any(String.class), eq(file))).willReturn(expectedReply);

        ResponseEntity<String> reply = subject.addStudents("Intro to CS", file);

        assertEquals(HttpStatus.NO_CONTENT, reply.getStatusCode());
        assertEquals(expectedReply, reply.getBody());
    }

    @Test
    void AddSubjectWithProperFile() {
        String expectedReply = "Students added";
        given(service.addStudents(any(String.class), eq(file))).willReturn(expectedReply);

        ResponseEntity<String> reply = subject.addStudents("Intro to CS", file);

        assertEquals(HttpStatus.CREATED, reply.getStatusCode());
        assertEquals(expectedReply, reply.getBody());
    }

    @Test
    void AddSubjectWithCorruptFile() {
        String expectedReply = "Foo Bar";
        given(service.addStudents(any(String.class), eq(file))).willReturn(expectedReply);

        ResponseEntity<String> reply = subject.addStudents("Intro to CS", file);

        assertEquals(HttpStatus.NOT_MODIFIED, reply.getStatusCode());
        assertEquals(expectedReply, reply.getBody());
    }

    @Test
    void getStudent() {
        String subjectName = "C";
        List<StudentSubject> list = new ArrayList<>();
        given(service.getStudents(subjectName)).willReturn(list);

        ResponseEntity<List<StudentSubject>> reply = subject.getStudent(subjectName);

        assertEquals(HttpStatus.OK, reply.getStatusCode());
    }
}