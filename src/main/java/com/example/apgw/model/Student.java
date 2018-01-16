package com.example.apgw.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import java.util.List;


@Entity
public class Student {
    @Id
    private String email;
    @NotNull
    private String name;
    @OneToMany(mappedBy = "student")

    @JsonManagedReference
    private List<StudentSubject> subjects;

    public Student(String email) {
        this.email = email;
    }

    public Student() {
    }

    public Student(String email, String name) {
        this.email = email;
        this.name = name;
    }

    public List<StudentSubject> getSubjects() {
        return subjects;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }


}
