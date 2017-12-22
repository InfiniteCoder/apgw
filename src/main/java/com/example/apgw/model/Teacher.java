package com.example.apgw.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
public class Teacher {

    @Id
    private String email;
    @NotNull
    private String name;
    @OneToMany(mappedBy = "teacher")
    @JsonManagedReference
    private List<Subject> subjects;

    public Teacher(String email, String name) {
        this.email = email;
        this.name = name;
    }

    private Teacher() {
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public List<Subject> getSubjects() {
        return subjects;
    }
}
