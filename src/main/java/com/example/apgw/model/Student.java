package com.example.apgw.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotNull;
import java.util.List;


@Entity
public class Student {
    @Id
    private String email;
    @NotNull
    private String name;
    @NotNull
    private int rollNo;
    @JsonManagedReference
    @ManyToMany(mappedBy = "students")
    private List<Subject> subjects;

    public Student() {
    }

    public Student(String email, String name, int rollNo) {
        this.email = email;
        this.name = name;
        this.rollNo = rollNo;
    }

    public List<Subject> getSubjects() {
        return subjects;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public int getRollNo() {
        return rollNo;
    }


}
