package com.example.apgw.service;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.json.simple.JSONObject;

import java.security.Principal;

@EqualsAndHashCode
@ToString
public class UserPrincipal implements Principal {
    private String email;
    private String name;
    private String picture;

    public UserPrincipal(Principal principal) {
        JSONObject jsonObject = UserObject.getUserDetails(principal);
        if (jsonObject != null) {
            this.email = jsonObject.get("email").toString();
            this.picture = jsonObject.get("picture").toString();
            this.name = jsonObject.get("name").toString();
        } else {
            this.email = "";
            this.picture = "";
            this.name = "";
        }

    }

    @Override
    public String getName() {
        return this.name;
    }

    public String getEmail() {
        return email;
    }

    public String getPicture() {
        return picture;
    }
}
