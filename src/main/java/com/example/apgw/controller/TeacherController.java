package com.example.apgw.controller;

import com.example.apgw.model.Subject;
import com.example.apgw.model.Teacher;
import com.example.apgw.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TeacherController {

    private final TeacherService teacherService;
    @Autowired
    public TeacherController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    /**
     * Create new teacher.
     *
     * @return Object of newly created teacher
     * @see Teacher
     */
    @PostMapping("/createTeacher")
    @ResponseBody
    public ResponseEntity<Teacher> createTeacher() {
        Teacher teacher = teacherService.createTeacher();
        return new ResponseEntity<>(teacher, HttpStatus.CREATED);
    }

    /**
     * Get the subjects of logged in teacher.
     *
     * @return List of subjects
     * @see Subject
     */
    @GetMapping("/teacher/subjects")
    @ResponseBody
    public ResponseEntity<List<Subject>> getSubjects() {
        return new ResponseEntity<>(teacherService.getSubjects(), HttpStatus.OK);
    }
}
