package com.example.apgw.controller;

import com.example.apgw.helper.User;
import com.example.apgw.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.MockitoAnnotations.initMocks;


class UserInfoControllerTest {

    @Mock
    private UserService userService;
    @Mock
    private Authentication authentication;

    private UserInfoController testSubject;

    @BeforeEach
    void setUp() {
        initMocks(this);
        testSubject = new UserInfoController(userService);
    }

    @Test
    void shouldReturnStudent() {
        given(userService.getType()).willReturn("student");
        ResponseEntity<String> reply = testSubject.getUserType();
        assertEquals("student", reply.getBody());
        assertEquals(HttpStatus.OK, reply.getStatusCode());
    }

    @Test
    void shouldReturnTeacher() {
        given(userService.getType()).willReturn("teacher");
        ResponseEntity<String> reply = testSubject.getUserType();
        assertEquals("teacher", reply.getBody());
        assertEquals(HttpStatus.OK, reply.getStatusCode());
    }

    @Test
    void shouldReturnNew() {
        given(userService.getType()).willReturn("new");
        ResponseEntity<String> reply = testSubject.getUserType();
        assertEquals("new", reply.getBody());
        assertEquals(HttpStatus.OK, reply.getStatusCode());
    }

    @Test
    void isAuthShouldReturnTrue() {
        given(authentication.isAuthenticated()).willReturn(true);
        boolean reply = testSubject.isAuth(authentication);
        assertEquals(true, reply);
    }

    @Test
    void isAuthShouldReturnFalse() {
        given(authentication.isAuthenticated()).willReturn(false);
        boolean reply = testSubject.isAuth(authentication);
        assertEquals(false, reply);
    }

    @Test
    void shouldReturnUser() {
        String email = "foo@example.com";
        String picture = "https://example.com/pic.jpg";
        String name = "John Doe";
        User user = new User(email, picture, name);
        given(userService.user()).willReturn(user);

        ResponseEntity<User> reply = testSubject.getUser();

        assertEquals(HttpStatus.OK, reply.getStatusCode());
    }
}