package com.example.apgw.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Subject {

    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne
    private SubjectDetails details;
    @ManyToOne
    @JsonBackReference
    private Teacher teacher;
    @OneToMany(mappedBy = "subject")
    @JsonManagedReference
    @Cascade(org.hibernate.annotations.CascadeType.DELETE)
    private List<StudentSubject> students;
    @OneToMany(mappedBy = "subject")
    @JsonManagedReference
    @Cascade(org.hibernate.annotations.CascadeType.DELETE)
    private List<Assignment> assignments;

    public Subject(SubjectDetails details, Teacher teacher) {
        this.details = details;
        this.teacher = teacher;
    }
}
