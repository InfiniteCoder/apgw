package com.example.apgw.model;


import lombok.EqualsAndHashCode;

import java.io.Serializable;

@EqualsAndHashCode
public class StudentSubjectId implements Serializable {
    private Long subjectId;
    private String studentEmail;

    public StudentSubjectId(Long subjectId, String studentEmail) {
        this.subjectId = subjectId;
        this.studentEmail = studentEmail;
    }

    public StudentSubjectId() {
    }
}
