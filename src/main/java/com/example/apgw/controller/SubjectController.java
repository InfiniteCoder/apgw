package com.example.apgw.controller;

import com.example.apgw.model.Subject;
import com.example.apgw.model.Teacher;
import com.example.apgw.repository.SubjectRepository;
import com.example.apgw.repository.TeacherRepository;
import com.example.apgw.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SubjectController {

    private final SubjectRepository subjectRepository;
    private final TeacherRepository teacherRepository;
    private final UserService userService;
    @Autowired
    public SubjectController(SubjectRepository subjectRepository,
                             TeacherRepository teacherRepository,
                             UserService userService) {
        this.subjectRepository = subjectRepository;
        this.teacherRepository = teacherRepository;
        this.userService = userService;
    }

    /**
     * add subject endpoint. Subject name must be unique for the teacher.
     *
     * @param name      name of subject
     * @return String message showing status
     */
    @PostMapping("/addSubject")
    @ResponseBody
    public ResponseEntity<String> addSubject(@RequestParam(name = "name") String name) {
        Teacher teacher = teacherRepository.findOne(userService.getEmail());
        Subject subject = new Subject(name, teacher);
        Subject subExist = subjectRepository.findByNameAndTeacher(name, teacher);
        if (subExist == null && !name.isEmpty()) {
            subjectRepository.save(subject);
            return new ResponseEntity<>("Subject added", HttpStatus.CREATED);
        } else if (subExist != null) {
            return new ResponseEntity<>("Subject already exists", HttpStatus.NOT_MODIFIED);
        } else {
            return new ResponseEntity<>("Name cannot be empty", HttpStatus.NO_CONTENT);

        }
    }
}
