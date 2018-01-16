package com.example.apgw.controller;

import com.example.apgw.model.StudentSubject;
import com.example.apgw.model.Subject;
import com.example.apgw.model.Teacher;
import com.example.apgw.repository.StudentSubjectRepository;
import com.example.apgw.repository.SubjectRepository;
import com.example.apgw.repository.TeacherRepository;
import com.example.apgw.service.StudentCSVParser;
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
import java.security.Principal;
import java.util.ArrayList;

@RestController
public class StudentSubjectController {

    private final StudentSubjectRepository studentSubjectRepository;
    private final TeacherRepository teacherRepository;
    private final SubjectRepository subjectRepository;

    @Autowired
    public StudentSubjectController(StudentSubjectRepository studentSubjectRepository,
                                    TeacherRepository teacherRepository,
                                    SubjectRepository subjectRepository) {
        this.studentSubjectRepository = studentSubjectRepository;
        this.teacherRepository = teacherRepository;
        this.subjectRepository = subjectRepository;
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
        if (file.isEmpty()) {
            return new ResponseEntity<>("Empty file", HttpStatus.NO_CONTENT);
        }

        //Get teacher
        UserPrincipal userPrincipal = new UserPrincipal(principal);
        String teacherEmail = userPrincipal.getEmail();
        Teacher teacher = teacherRepository.findOne(teacherEmail);

        //Get Subject
        Subject subject = subjectRepository.findByNameAndTeacher(subjectName, teacher);

        //parse CSV
        try {
            ArrayList<StudentSubject> list = StudentCSVParser.parse(file, subject.getId());
            studentSubjectRepository.save(list);
            return new ResponseEntity<>("Students added", HttpStatus.CREATED);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>("Parsing Error", HttpStatus.NOT_MODIFIED);
        }
    }
}
