package com.example.apgw.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import java.util.List;


@Entity
@Getter
@Setter
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

    /**
     * Create new student based on email and name.
     * Should be taken from Gmail API.
     *
     * @param email Email id of student.
     * @param name  Name of student.
     */
    public Student(String email, String name) {
        this.email = email;
        this.name = name;
    }

}
