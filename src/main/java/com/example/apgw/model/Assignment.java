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
    private String title;
    private String inputPath;
    private String outputPath;
    private String questionPath;
    @OneToMany(mappedBy = "assignment")
    private List<Submission> submissions;

    /**
     *
     * @param subject subject to which assignment is associated
     * @param title title of assignment
     * @param inputPath path to file storing test case input
     * @param outputPath path to file storing test case output
     * @param questionPath path to file storing question text
     */
    public Assignment(Subject subject,
                      String title,
                      String inputPath,
                      String outputPath,
                      String questionPath) {
        this.subject = subject;
        this.title = title;
        this.inputPath = inputPath;
        this.outputPath = outputPath;
        this.questionPath = questionPath;
    }

    public Assignment(Subject subject) {
        this.subject = subject;
    }

    private Assignment() {
    }
}
