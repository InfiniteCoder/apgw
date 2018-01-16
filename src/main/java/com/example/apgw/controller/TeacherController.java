package com.example.apgw.controller;

import com.example.apgw.model.Subject;
import com.example.apgw.model.Teacher;
import com.example.apgw.repository.TeacherRepository;
import com.example.apgw.service.TeacherService;
import com.example.apgw.service.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
public class TeacherController {

    private final TeacherRepository teacherRepository;

    @Autowired
    public TeacherController(TeacherRepository teacherRepository) {
        this.teacherRepository = teacherRepository;
    }

    /**
     * Create new teacher
     *
     * @param principal Provided by Spring
     * @return Object of newly created teacher
     * @see Teacher
     */
    @PostMapping("/createTeacher")
    @ResponseBody
    public ResponseEntity<Teacher> createTeacher(Principal principal) {
        UserPrincipal userPrincipal = new UserPrincipal(principal);
        Teacher teacher = new TeacherService(userPrincipal).createTeacher();
        teacherRepository.save(teacher);
        return new ResponseEntity<>(teacher, HttpStatus.CREATED);
    }

    /**
     * Get the subjects of logged in teacher
     * @param principal Provided by Spring
     * @return List of subjects
     * @see Subject
     */
    @GetMapping("/teacher/subjects")
    @ResponseBody
    public List<Subject> getSubjects(Principal principal) {
        UserPrincipal userPrincipal = new UserPrincipal(principal);
        Teacher teacher = teacherRepository.findOne(userPrincipal.getEmail());
        return teacher.getSubjects();
    }
}
