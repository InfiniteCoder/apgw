package com.example.apgw.service;

import com.example.apgw.model.Student;
import com.example.apgw.model.StudentSubject;
import com.example.apgw.model.Subject;
import com.example.apgw.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StudentService {
    private final UserService userService;
    private final StudentRepository studentRepository;

    @Autowired
    public StudentService(UserService userService, StudentRepository studentRepository) {
        this.userService = userService;
        this.studentRepository = studentRepository;
    }


    /**
     * Create new Student. Pass the userPrincipal object to constructor
     *
     * @return Student object
     * @see UserPrincipal
     */
    public Student createStudent() {
        String email = userService.getEmail();
        String name = userService.getName();
        Student student = new Student(email, name);
        studentRepository.save(student);
        return student;
    }

    public List<Subject> getSubjects() {
        String email = userService.getEmail();
        Student student = studentRepository.findOne(email);
        List<StudentSubject> list = student.getSubjects();

        List<Subject> subjects = new ArrayList<>();
        for (StudentSubject item : list) {
            subjects.add(item.getSubject());
        }

        return subjects;
    }

}
