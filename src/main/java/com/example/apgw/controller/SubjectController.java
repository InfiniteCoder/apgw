package com.example.apgw.controller;

import com.example.apgw.model.Subject;
import com.example.apgw.model.Teacher;
import com.example.apgw.repository.SubjectRepository;
import com.example.apgw.repository.TeacherRepository;
import com.example.apgw.service.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.Principal;

@RestController
public class SubjectController {

    private final SubjectRepository subjectRepository;
    private final TeacherRepository teacherRepository;

    @Autowired
    public SubjectController(SubjectRepository subjectRepository, TeacherRepository teacherRepository) {
        this.subjectRepository = subjectRepository;
        this.teacherRepository = teacherRepository;
    }

    @PostMapping("/addSubject")
    @ResponseBody
    public ResponseEntity<String> addSubject(Principal principal,
                           @RequestParam(name = "name") String name) {
        UserPrincipal userPrincipal = new UserPrincipal(principal);
        Teacher teacher = teacherRepository.findOne(userPrincipal.getEmail());
        Subject subject = new Subject(name, teacher);
        Subject subExist = subjectRepository.findByTeacherAndName(teacher, name);
        if (subExist == null && !name.isEmpty()) {
            subjectRepository.save(subject);
            return new ResponseEntity<>("Subject added", HttpStatus.CREATED);
        } else if (subExist != null) {
            return new ResponseEntity<>("Subject already exists", HttpStatus.NOT_MODIFIED);
        } else {
            return new ResponseEntity<>("Name cannot be empty", HttpStatus.NOT_MODIFIED);

        }
    }

    @PostMapping("/addStudents")
    public ResponseEntity<String> addStudent(Principal principal, @RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return new ResponseEntity<>("Empty file", HttpStatus.NO_CONTENT);
        }
        try {
            byte[] bytes = file.getBytes();
            String data = new String(bytes, StandardCharsets.UTF_8);
            System.out.println(data);
            return new ResponseEntity<>("Uploaded", HttpStatus.CREATED);

        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>("Error while parsing", HttpStatus.NOT_MODIFIED);
        }
    }
}
