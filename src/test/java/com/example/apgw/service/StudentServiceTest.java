package com.example.apgw.service;

import com.example.apgw.model.Student;
import com.example.apgw.repository.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.MockitoAnnotations.initMocks;

class StudentServiceTest {

    private StudentService studentService;
    @Mock
    private UserService userService;
    @Mock
    private StudentRepository repository;

    @BeforeEach
    void setUp() {
        initMocks(this);
        studentService = new StudentService(userService, repository);
    }

    @Test
    void createStudent() {
        String email = "example@gmail.com";
        String name = "John Doe";
        given(userService.getEmail()).willReturn(email);
        given(userService.getName()).willReturn(name);

        Student student = studentService.createStudent();

        assertEquals(name, student.getName());
        assertEquals(email, student.getEmail());
    }
}