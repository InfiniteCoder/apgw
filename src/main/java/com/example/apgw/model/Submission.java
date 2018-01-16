package com.example.apgw.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
@Data
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

    /**
     * Constructor of submission.
     * Submission stores a specific submission from a student
     *
     * @param assignment Assignment to which submission belongs
     * @param student    Student who submitted the code
     * @param uploadPath File pointing to submitted code
     */
    public Submission(Assignment assignment, Student student, String uploadPath) {
        this.assignment = assignment;
        this.student = student;
        this.uploadPath = uploadPath;
    }

    private Submission() {
    }
}
