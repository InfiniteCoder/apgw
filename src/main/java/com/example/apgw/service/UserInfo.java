package com.example.apgw.service;

import com.example.apgw.model.User;
import org.json.simple.JSONObject;

import java.security.Principal;

public class UserInfo {
    private Principal principal;

    public UserInfo(Principal principal) {
        this.principal = principal;
    }

    public String userEmail() {
        JSONObject userDetails = UserObject.getUserDetails(principal);
        if (userDetails != null) {
            return userDetails.get("email").toString();
        } else {
            return "email not found";
        }
    }

    public String userName() {
        JSONObject userDetails = UserObject.getUserDetails(principal);
        if (userDetails != null) {
            return userDetails.get("name").toString();
        } else {
            return "name not found";
        }
    }

    public String userPicture() {
        JSONObject userDetails = UserObject.getUserDetails(principal);
        if (userDetails != null) {
            return userDetails.get("picture").toString();
        } else {
            return "picture not found";
        }
    }

    public User getUser() {
        String email = userEmail();
        String name = userName();
        String picture = userPicture();
        return new User(email, picture, name);
    }
}
