package com.example.apgw.controller;

import com.example.apgw.model.StudentSubject;
import com.example.apgw.service.StudentSubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.List;

@RestController
public class StudentSubjectController {


    private final StudentSubjectService studentSubjectService;
    @Autowired
    public StudentSubjectController(StudentSubjectService studentSubjectService) {
        this.studentSubjectService = studentSubjectService;
    }

    /**
     * Add students to a subject. Returns String message containing
     * error message or success message
     *
     * @param principal   Provided by Spring
     * @param subjectName Name of subject
     * @param file        CSV file containing student info
     * @return Returns string containing success/error message.
     * @see MultipartFile
     */
    @PostMapping(value = "/addStudents")
    @ResponseBody
    public ResponseEntity<String> addSubject(Principal principal,
                                             @RequestParam("subject") String subjectName,
                                             @RequestParam("file") MultipartFile file) {

        String reply = studentSubjectService.addSubject(principal, subjectName, file);
        switch (reply) {
            case "Empty File":
                return new ResponseEntity<>(reply, HttpStatus.NO_CONTENT);
            case "Students added":
                return new ResponseEntity<>(reply, HttpStatus.CREATED);
            default:
                return new ResponseEntity<>(reply, HttpStatus.NOT_MODIFIED);
        }
    }

    @GetMapping("/api/getStudents")
    public ResponseEntity<List<StudentSubject>> getStudent(Principal principal,
                                                           @RequestParam(name = "subjectName")
                                                                   String subjectName) {
        List<StudentSubject> list = studentSubjectService.getStudents(principal, subjectName);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }
}
