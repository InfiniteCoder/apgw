package com.example.apgw.controller;

import com.example.apgw.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import java.security.Principal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.MockitoAnnotations.initMocks;


class UserInfoControllerTest {

    @Mock
    private UserService userService;
    @Mock
    private Principal principal;
    @Mock
    private Authentication authentication;

    private UserInfoController subject;

    @BeforeEach
    void setUp() {
        initMocks(this);
        subject = new UserInfoController(userService);
    }

    @Test
    void shouldReturnStudent() {
        given(userService.getType()).willReturn("student");
        ResponseEntity<String> reply = subject.getUserType();
        assertEquals("student", reply.getBody());
        assertEquals(HttpStatus.OK, reply.getStatusCode());
    }

    @Test
    void shouldReturnTeacher() {
        given(userService.getType()).willReturn("teacher");
        ResponseEntity<String> reply = subject.getUserType();
        assertEquals("teacher", reply.getBody());
        assertEquals(HttpStatus.OK, reply.getStatusCode());
    }

    @Test
    void shouldReturnNew() {
        given(userService.getType()).willReturn("new");
        ResponseEntity<String> reply = subject.getUserType();
        assertEquals("new", reply.getBody());
        assertEquals(HttpStatus.OK, reply.getStatusCode());
    }

    @Test
    void isAuthShouldReturnTrue() {
        given(authentication.isAuthenticated()).willReturn(true);
        boolean reply = subject.isAuth(authentication);
        assertEquals(true, reply);
    }

    @Test
    void isAuthShouldReturnFalse() {
        given(authentication.isAuthenticated()).willReturn(false);
        boolean reply = subject.isAuth(authentication);
        assertEquals(false, reply);
    }
}