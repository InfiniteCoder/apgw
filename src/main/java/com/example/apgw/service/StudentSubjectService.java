package com.example.apgw.service;

import com.example.apgw.helper.StudentCsvParser;
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
import java.util.ArrayList;
import java.util.List;

@Service
public class StudentSubjectService {

    private final SubjectRepository subjectRepository;
    private final TeacherRepository teacherRepository;
    private final StudentSubjectRepository studentSubjectRepository;
    private final UserService userService;

    /**
     * Constructor. Generally not required to use directly.
     *
     * @param subjectRepository        Subject repository.
     * @param teacherRepository        Teacher repository.
     * @param studentSubjectRepository StudentSubject repository.
     * @param userService              UserService object.
     */
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

    /**
     * Get list of students.
     *
     * @param subjectName Name of subject.
     * @return List of subjects.
     * @see StudentSubject
     */
    public List<StudentSubject> getStudents(String subjectName) {
        String email = userService.getEmail();
        Teacher teacher = teacherRepository.findOne(email);
        Subject subject = subjectRepository.findByNameAndTeacher(subjectName, teacher);
        return subject.getStudents();
    }

    /**
     * Add students to a subject. Returns String message containing
     * error message or success message
     *
     * @param subjectName Name of subject
     * @param file        CSV file containing student info
     * @return Returns string containing success/error message.
     * @see MultipartFile
     */
    public String addStudents(String subjectName, MultipartFile file) {
        if (file.isEmpty()) {
            return "Empty file";
        }

        //Get teacher
        String teacherEmail = userService.getEmail();
        Teacher teacher = teacherRepository.findOne(teacherEmail);
        //Get Subject
        Subject subject = subjectRepository.findByNameAndTeacher(subjectName, teacher);

        //parse CSV
        try {
            ArrayList<StudentSubject> list = StudentCsvParser.parse(file, subject.getId());
            studentSubjectRepository.save(list);
            return "Students added";
        } catch (IOException e) {
            e.printStackTrace();
            return "Parsing Error";
        }
    }
}
