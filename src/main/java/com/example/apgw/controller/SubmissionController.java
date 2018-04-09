package com.example.apgw.controller;

import com.example.apgw.service.SubmissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.acl.NotOwnerException;

@Controller
public class SubmissionController {

    final
    SubmissionService service;

    @Autowired
    public SubmissionController(SubmissionService service) {
        this.service = service;
    }

    /**
     * Add a submission.
     *
     * @param assignmentId id of assignment.
     * @param file         submitted file.
     * @return Error/success message with HttpStatus.
     */
    @PostMapping("/api/addSubmission")
    public ResponseEntity<String> addSubmission(
            @RequestParam(name = "id") Long assignmentId,
            @RequestParam(name = "file") MultipartFile file) {
        try {
            service.addSubmission(assignmentId, file);
            return new ResponseEntity<>("created", HttpStatus.CREATED);
        } catch (NotOwnerException e) {
            e.printStackTrace();
            return new ResponseEntity<>("Permission denied", HttpStatus.NOT_MODIFIED);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>("I/O Error", HttpStatus.NOT_MODIFIED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_MODIFIED);
        }
    }
}
