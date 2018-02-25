package com.example.apgw.controller;

import com.example.apgw.model.Submission;
import com.example.apgw.service.SubmissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
public class SubmissionController {

    final
    SubmissionService service;

    @Autowired
    public SubmissionController(SubmissionService service) {
        this.service = service;
    }

    @PostMapping("/api/addSubmission")
    public ResponseEntity<String> addSubmission(@RequestParam(name = "id") Long assignmentId,
                                                @RequestParam(name = "file") MultipartFile file) {
        String reply = service.addSubmission(assignmentId, file);
        switch (reply) {
            case "Permission denied":
            case "Error creating dir":
            case "FS Error":
                return new ResponseEntity<>(reply, HttpStatus.NOT_MODIFIED);

            default:
                return new ResponseEntity<>(reply, HttpStatus.CREATED);
        }
    }

    @GetMapping("/api/submissions")
    public ResponseEntity<List<Submission>> submission(@RequestParam(name = "id") Long assignmentId) {
        List<Submission> list = service.getSubmissions(assignmentId);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }
}
