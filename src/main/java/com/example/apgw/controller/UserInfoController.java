package com.example.apgw.controller;

import com.example.apgw.model.User;
import com.example.apgw.service.UserInfo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class UserInfoController {

    @GetMapping(value = "/user")
    public ResponseEntity<User> getUser(Principal principal) {
        return new ResponseEntity<>(new UserInfo(principal).getUser(), HttpStatus.OK);
    }
}
