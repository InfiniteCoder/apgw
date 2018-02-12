package com.example.apgw.service;

import com.example.apgw.model.StudentSubject;
import com.example.apgw.model.Subject;
import com.example.apgw.model.Teacher;
import com.example.apgw.repository.StudentSubjectRepository;
import com.example.apgw.repository.SubjectRepository;
import com.example.apgw.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Service
public class StudentSubjectService {

    private final SubjectRepository subjectRepository;
    private final TeacherRepository teacherRepository;
    private final StudentSubjectRepository studentSubjectRepository;
    private final UserService userService;

    @Autowired
    public StudentSubjectService(SubjectRepository subjectRepository,
                                 TeacherRepository teacherRepository,
                                 StudentSubjectRepository studentSubjectRepository,
                                 UserService userService) {
        this.subjectRepository = subjectRepository;
        this.teacherRepository = teacherRepository;
        this.studentSubjectRepository = studentSubjectRepository;
        this.userService = userService;
    }

    public List<StudentSubject> getStudents(Principal principal, String subjectName) {
        String email = userService.getEmail(principal);
        Teacher teacher = teacherRepository.findOne(email);
        Subject subject = subjectRepository.findByNameAndTeacher(subjectName, teacher);
        return subject.getStudents();
    }

    public String addSubject(Principal principal, String subjectName, MultipartFile file) {
        if (file.isEmpty()) {
            return "Empty file";
        }

        //Get teacher
        String teacherEmail = userService.getEmail(principal);
        Teacher teacher = teacherRepository.findOne(teacherEmail);
        //Get Subject
        Subject subject = subjectRepository.findByNameAndTeacher(subjectName, teacher);

        //parse CSV
        try {
            ArrayList<StudentSubject> list = StudentCSVParser.parse(file, subject.getId());
            studentSubjectRepository.save(list);
            return "Students added";
        } catch (IOException e) {
            e.printStackTrace();
            return "Parsing Error";
        }
    }
}
