package com.example.apgw.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
@Data
@ToString
public class Submission {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JsonBackReference
    private Assignment assignment;
    @ManyToOne
    private Student student;
    private int marks;

    /**
     * Constructor of submission.
     * Submission stores a specific submission from a student
     *
     * @param assignment Assignment to which submission belongs
     * @param student    Student who submitted the code
     */
    public Submission(Assignment assignment, Student student) {
        this.assignment = assignment;
        this.student = student;
    }

    private Submission() {
    }
}
