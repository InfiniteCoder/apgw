package com.example.apgw.model;


public class User {
    private String email;
    private String picture;
    private String name;

    public User(String email, String picture, String name) {
        this.email = email;
        this.picture = picture;
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
