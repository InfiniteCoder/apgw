package com.example.apgw.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
public class Subject {

    @Id
    @GeneratedValue
    private Long id;
    @NotNull
    @NotBlank
    private String name;
    @ManyToOne
    @JsonBackReference
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

    public void setStudents(List<Student> students) {
        this.students = students;
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

    //add student list


}
