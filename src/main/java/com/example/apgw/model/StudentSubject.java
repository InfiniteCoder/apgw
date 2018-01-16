package com.example.apgw.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@IdClass(StudentSubjectId.class)
@Data
public class StudentSubject implements Serializable {

    //Primary keys
    @Id
    @Column(name = "subject_id")
    Long subjectId;
    @Id
    @Column(name = "student_email")
    String studentEmail;

    String uid;   //eg. Roll No
    @ManyToOne
    @JoinColumn(name = "subject_id", insertable = false, updatable = false)
    private Subject subject;
    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "student_email", insertable = false, updatable = false)
    private Student student;

    public StudentSubject(Long subjectId, String studentEmail, String uid) {
        this.subjectId = subjectId;
        this.studentEmail = studentEmail;
        this.uid = uid;
    }

    public StudentSubject() {
    }
}
