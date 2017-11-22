package com.example.apgw.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
public class Subject {

    @Id
    @GeneratedValue
    private Long id;
    @NotNull
    private String name;
    @ManyToOne
    private Teacher teacher;
    @ManyToMany
    private List<Student> students;
    @OneToMany(mappedBy = "subject")
    private List<Assignment> assignments;

    public Subject(String name, Teacher teacher) {
        this.name = name;
        this.teacher = teacher;
    }

    private Subject() {
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    //needs a method to convert CSV to studentList
}
