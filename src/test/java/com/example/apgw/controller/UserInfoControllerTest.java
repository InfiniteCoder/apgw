package com.example.apgw.controller;

import com.example.apgw.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.security.Principal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.MockitoAnnotations.initMocks;

class UserInfoControllerTest {

    @Mock
    private UserService userService;
    @Mock
    private Principal principal;
    private UserInfoController subject;

    @BeforeEach
    void setUp() {
        initMocks(this);
        subject = new UserInfoController(userService);
    }

    @Test
    void shouldReturnStudent() {
        given(userService.getType(any(Principal.class))).willReturn("student");
        ResponseEntity<String> reply = subject.getUserType(principal);
        assertEquals("student", reply.getBody());
        assertEquals(HttpStatus.OK, reply.getStatusCode());
    }

    @Test
    void shouldReturnTeacher() {
        given(userService.getType(any(Principal.class))).willReturn("teacher");
        ResponseEntity<String> reply = subject.getUserType(principal);
        assertEquals("teacher", reply.getBody());
        assertEquals(HttpStatus.OK, reply.getStatusCode());
    }

    @Test
    void shouldReturnNew() {
        given(userService.getType(any(Principal.class))).willReturn("new");
        ResponseEntity<String> reply = subject.getUserType(principal);
        assertEquals("new", reply.getBody());
        assertEquals(HttpStatus.OK, reply.getStatusCode());
    }
}