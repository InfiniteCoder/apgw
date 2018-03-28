package com.example.apgw.controller;

import com.example.apgw.model.SubjectDetails;
import com.example.apgw.service.SubjectDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class SubjectDetailsController {

    private final SubjectDetailsService service;

    @Autowired
    public SubjectDetailsController(SubjectDetailsService service) {
        this.service = service;
    }

    @GetMapping(value = "/api/dept")
    public ResponseEntity<List<SubjectDetails>> getAllDept() {
        List<SubjectDetails> list = service.getAllDept();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping(value = "/api/year")
    public ResponseEntity<List<SubjectDetails>> getAllYear(String dept) {
        List<SubjectDetails> list = service.getAllYear(dept);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping(value = "/api/name")
    public ResponseEntity<List<SubjectDetails>> getAllName(String dept, String year) {
        List<SubjectDetails> list = service.getAllName(dept, year);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }


}
