package com.example.apgw.helper;


import lombok.Data;

@Data
public class User {
    private String email;
    private String picture;
    private String name;

    /**
     * Constructor for User.
     * User stores common information for all users,
     * acquired from Google.
     *
     * @param email   Email as String
     * @param picture Url for picture
     * @param name    Name as String. Use full name, eg: John Doe,
     *                not John or J. Doe
     */
    public User(String email, String picture, String name) {
        this.email = email;
        this.picture = picture;
        this.name = name;
    }
}
