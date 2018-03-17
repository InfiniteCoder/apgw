package com.example.apgw.controller;

import com.example.apgw.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.acl.NotOwnerException;

@RestController
public class SubjectController {

    private final SubjectService service;

    @Autowired
    public SubjectController(SubjectService service) {
        this.service = service;
    }

    /**
     * add subject endpoint. Subject name must be unique for the teacher.
     *
     * @param name name of subject
     * @return String message showing status
     */
    @PostMapping("/addSubject")
    @ResponseBody
    public ResponseEntity<String> addSubject(@RequestParam(name = "name") String name) {
        String reply = service.addSubject(name);
        switch (reply) {
            case "Subject added":
                return new ResponseEntity<>(reply, HttpStatus.CREATED);
            case "Subject already exists":
                return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
            default:
                return new ResponseEntity<>(reply, HttpStatus.NO_CONTENT);

        }
    }

    /**
     * delete a subject.
     *
     * @param id id of subject to be deleted.
     * @return success status.
     */
    @DeleteMapping("/api/subject")
    public ResponseEntity<String> deleteSubject(Long id) {
        try {
            service.deleteSubject(id);
            return new ResponseEntity<>("subject deleted", HttpStatus.OK);
        } catch (NotOwnerException e) {
            e.printStackTrace();
            return new ResponseEntity<>("not owner", HttpStatus.NOT_FOUND);
        }
    }
}
