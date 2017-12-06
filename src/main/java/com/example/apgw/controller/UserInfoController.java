package com.example.apgw.controller;

import com.example.apgw.model.User;
import com.example.apgw.service.UserInfo;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class UserInfoController {

    @RequestMapping("/user")
    public User getUser(Principal principal) {
        return new UserInfo(principal).getUser();
    }
}
