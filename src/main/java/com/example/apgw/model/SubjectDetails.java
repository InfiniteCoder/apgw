package com.example.apgw.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
@NoArgsConstructor
public class SubjectDetails {
    @Id
    @GeneratedValue
    Long id;
    private String name;
    private String dept;
    private String year;

    /**
     * Constructor for SubjectDetails.
     *
     * @param name name of subject.
     * @param dept name of dept.
     * @param year year of subject.
     */
    public SubjectDetails(String name, String dept, String year) {
        this.name = name;
        this.dept = dept;
        this.year = year;
    }
}
