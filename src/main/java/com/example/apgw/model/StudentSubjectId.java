package com.example.apgw.model;


import lombok.EqualsAndHashCode;

import java.io.Serializable;

@EqualsAndHashCode
public class StudentSubjectId implements Serializable {
    private Long subjectId;
    private String studentEmail;
}
