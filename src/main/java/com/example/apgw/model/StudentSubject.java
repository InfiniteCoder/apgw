package com.example.apgw.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.ManyToOne;

@Entity
@IdClass(StudentSubjectId.class)
public @Data
class StudentSubject {

    String studentId;   //eg. Roll No
    @ManyToOne
    @Id
    private Subject subject;
    @ManyToOne
    @Id
    private Student student;


}
