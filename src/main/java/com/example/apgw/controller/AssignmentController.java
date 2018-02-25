package com.example.apgw.controller;

import com.example.apgw.model.Assignment;
import com.example.apgw.service.AssignmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
public class AssignmentController {

    private final AssignmentService service;

    @Autowired
    public AssignmentController(AssignmentService service) {
        this.service = service;
    }

    @PostMapping("/api/addAssignment")
    public ResponseEntity<String> addAssignment(@RequestParam(name = "subjectName") String subjectName,
                                                @RequestParam(name = "title") String title,
                                                @RequestParam(name = "inputFile") MultipartFile inputFile,
                                                @RequestParam(name = "outputFile") MultipartFile outputFile,
                                                @RequestParam(name = "questionFile") MultipartFile questionFile) {
        String reply = service.addAssignment(subjectName, title, inputFile, outputFile, questionFile);
        switch (reply) {
            case "Empty title":
            case "Empty file":
                return new ResponseEntity<>(reply, HttpStatus.NO_CONTENT);
            case "FS error":
            case "Error creating dir":
                return new ResponseEntity<>(reply, HttpStatus.NOT_MODIFIED);
            default:
                return new ResponseEntity<>(reply, HttpStatus.CREATED);

        }
    }

    @GetMapping("/api/assignments")
    public ResponseEntity<List<Assignment>> getAssignments(@RequestParam(name = "subjectName") String subjectName) {
        List<Assignment> list = service.getAssignments(subjectName);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/api/assignmentsById")
    public ResponseEntity<List<Assignment>> getAssignmentsById(Long subjectId) {
        List<Assignment> list = service.getAssignmentsById(subjectId);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }
}
