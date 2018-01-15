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
    @OneToMany(mappedBy = "student")
    private List<StudentSubject> students;
    @OneToMany(mappedBy = "subject")
    private List<Assignment> assignments;
    public Subject(String name, Teacher teacher) {
        this.name = name;
        this.teacher = teacher;
    }

    private Subject() {
    }

    public List<StudentSubject> getStudents() {
        return students;
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

    public void setStudents(List<StudentSubject> students) {
        this.students = students;
    }

    public List<Assignment> getAssignments() {
        return assignments;
    }
}
