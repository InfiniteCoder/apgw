package com.example.apgw.model;


import lombok.Data;

@Data
public class User {
    private String email;
    private String picture;
    private String name;

    public User(String email, String picture, String name) {
        this.email = email;
        this.picture = picture;
        this.name = name;
    }
}
