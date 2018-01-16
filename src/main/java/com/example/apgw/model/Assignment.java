package com.example.apgw.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class Assignment {
    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne
    private Subject subject;
    private String inputPath;
    private String outputPath;
    private String questionPath;
    @OneToMany(mappedBy = "assignment")
    private List<Submission> submissions;

    public Assignment(Subject subject, String inputPath, String outputPath, String questionPath) {
        this.subject = subject;
        this.inputPath = inputPath;
        this.outputPath = outputPath;
        this.questionPath = questionPath;
    }

    private Assignment() {
    }
}
