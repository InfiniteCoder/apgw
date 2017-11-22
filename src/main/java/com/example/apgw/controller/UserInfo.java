package com.example.apgw.controller;

import com.example.apgw.model.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class UserInfo {

    @RequestMapping("/user")
    public User getUser(Principal principal) {
        String email = userEmail(principal);
        String name = userName(principal);
        String picture = userPicture(principal);
        return new User(email, picture, name);
    }

    String userEmail(Principal principal) {
        JSONObject userDetails = getUserDetails(principal);
        if (userDetails != null) {
            return userDetails.get("email").toString();
        } else {
            return "email not found";
        }
    }

    String userName(Principal principal) {
        JSONObject userDetails = getUserDetails(principal);
        if (userDetails != null) {
            return userDetails.get("name").toString();
        } else {
            return "name not found";
        }
    }

    private String userPicture(Principal principal) {
        JSONObject userDetails = getUserDetails(principal);
        if (userDetails != null) {
            return userDetails.get("picture").toString();
        } else {
            return "name not found";
        }
    }

    private JSONObject getUserDetails(Principal principal) {
        ObjectWriter objectWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();
        try {
            String principalString = objectWriter.writeValueAsString(principal);
            Object principalObject = JSONValue.parse(principalString);
            JSONObject principalJson = (JSONObject) principalObject;
            JSONObject userAuthentication = (JSONObject) principalJson.get("userAuthentication");
            return (JSONObject) userAuthentication.get("details");
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
