package com.example.apgw.service;

import com.example.apgw.model.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.security.Principal;

public class UserInfo {
    private Principal principal;

    public UserInfo(Principal principal) {
        this.principal = principal;
    }

    public String userEmail() {
        JSONObject userDetails = getUserDetails();
        if (userDetails != null) {
            return userDetails.get("email").toString();
        } else {
            return "email not found";
        }
    }

    public String userName() {
        JSONObject userDetails = getUserDetails();
        if (userDetails != null) {
            return userDetails.get("name").toString();
        } else {
            return "name not found";
        }
    }

    public String userPicture() {
        JSONObject userDetails = getUserDetails();
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

    private JSONObject getUserDetails() {
        ObjectWriter objectWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();
        try {
            String principalString = objectWriter.writeValueAsString(this.principal);
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
