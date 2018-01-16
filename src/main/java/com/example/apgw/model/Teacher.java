package com.example.apgw.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Data
public class Teacher {

    @Id
    private String email;
    @NotNull
    private String name;
    @OneToMany(mappedBy = "teacher")
    @JsonManagedReference
    private List<Subject> subjects;

    /**
     * Constructor for Teacher
     *
     * @param email email id of teacher
     * @param name  name of teacher. Full name preferred
     */
    public Teacher(String email, String name) {
        this.email = email;
        this.name = name;
    }

    private Teacher() {
    }
}
