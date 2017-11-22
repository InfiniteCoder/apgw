package com.example.apgw.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Submission {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private Assignment assignment;
    @ManyToOne
    private Student student;
    private String uploadPath;
    private int marks;

    public Submission(Assignment assignment, Student student, String uploadPath) {
        this.assignment = assignment;
        this.student = student;
        this.uploadPath = uploadPath;
    }

    private Submission() {
    }

    public Long getId() {
        return id;
    }

    public Assignment getAssignment() {
        return assignment;
    }

    public Student getStudent() {
        return student;
    }

    public String getUploadPath() {
        return uploadPath;
    }

    public int getMarks() {
        return marks;
    }

    public void setMarks(int marks) {
        this.marks = marks;
    }
}
