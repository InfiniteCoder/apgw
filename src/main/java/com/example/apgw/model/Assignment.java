package com.example.apgw.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
public class Assignment {
    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne
    @JsonBackReference
    private Subject subject;
    private String title;
    @JsonManagedReference
    @OneToMany(mappedBy = "assignment")
    @Cascade(org.hibernate.annotations.CascadeType.DELETE)
    private List<Submission> submissions;

    public Assignment(Subject subject, String title) {
        this.subject = subject;
        this.title = title;
    }

    public Assignment() {
    }
}
