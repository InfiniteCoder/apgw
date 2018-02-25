package com.example.apgw.service;

import com.example.apgw.model.Subject;
import com.example.apgw.model.Teacher;
import com.example.apgw.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeacherService {
    private final UserService userService;
    private final TeacherRepository teacherRepository;

    /**
     * Used to create Teacher.
     *
     * @param userService UserService object which provides user details
     */
    @Autowired
    public TeacherService(UserService userService,
                          TeacherRepository teacherRepository) {
        this.userService = userService;
        this.teacherRepository = teacherRepository;
    }

    public Teacher createTeacher() {
        String email = userService.getEmail();
        String name = userService.getName();
        Teacher teacher = new Teacher(email, name);
        teacherRepository.save(teacher);
        return teacher;

    }

    public List<Subject> getSubjects() {
        Teacher teacher = teacherRepository.findOne(userService.getEmail());
        return teacher.getSubjects();
    }
}
