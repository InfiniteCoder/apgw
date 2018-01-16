package com.example.apgw.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Data
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

    /**
     * Constructor for subject.
     *
     * @param name    Name of subject
     * @param teacher Object of teacher to whom the subject belongs
     */
    public Subject(String name, Teacher teacher) {
        this.name = name;
        this.teacher = teacher;
    }

    private Subject() {
    }
}
