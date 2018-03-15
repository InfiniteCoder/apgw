package com.example.apgw.controller;

import com.example.apgw.helper.User;
import com.example.apgw.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserInfoController {

    private final UserService userService;

    @Autowired
    public UserInfoController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Get email, name and picture of logged in user.
     * Picture is an url pointing to Google servers.
     *
     * @return email, name and picture of user.
     */
    @GetMapping(value = "/user")
    public ResponseEntity<User> getUser() {
        User user = userService.user();
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    /**
     * Check if user is Authenticated(logged in).
     *
     * @param authentication Provided by Spring.
     * @return true or false, suggesting if user is authenticated.
     */
    @GetMapping(value = "/all/isUserAuth")
    public boolean isAuth(Authentication authentication) {
        return authentication != null && authentication.isAuthenticated();
    }

    /**
     * Get Type of user. Type is either teacher, student or new.
     *
     * @return Type of user as String.
     */
    @GetMapping(value = "/userType")
    public ResponseEntity<String> getUserType() {
        String type = userService.getType();
        return new ResponseEntity<>(type, HttpStatus.OK);
    }
}
