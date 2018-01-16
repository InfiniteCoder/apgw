package com.example.apgw.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.security.Principal;

/**
 * converts Principle class to JSONObject object
 */
public class UserObject {

    /**
     * Creates a JSONObject containing user's authentication details from Principal
     *
     * @param principal Principal object containing data of user
     * @return JSONObject containing part of principal's data (userAuthentication -> details)
     */
    public static JSONObject getUserDetails(Principal principal) {
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
