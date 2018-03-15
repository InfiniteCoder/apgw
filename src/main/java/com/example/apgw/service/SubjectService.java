package com.example.apgw.service;

import com.example.apgw.model.Subject;
import com.example.apgw.model.Teacher;
import com.example.apgw.repository.SubjectRepository;
import com.example.apgw.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SubjectService {

    private final TeacherRepository teacherRepository;
    private final UserService userService;
    private final SubjectRepository subjectRepository;

    /**
     * Controller for Subject Service.
     *
     * @param teacherRepository Repository for Teacher.
     * @param userService       Repository for UserService.
     * @param subjectRepository Repository for Subject.
     */
    @Autowired
    public SubjectService(TeacherRepository teacherRepository,
                          UserService userService,
                          SubjectRepository subjectRepository) {
        this.teacherRepository = teacherRepository;
        this.userService = userService;
        this.subjectRepository = subjectRepository;
    }

    /**
     * add subject endpoint. Subject name must be unique for the teacher.
     *
     * @param name name of subject
     * @return String message showing status
     */
    public String addSubject(String name) {
        Teacher teacher = teacherRepository.findOne(userService.getEmail());
        Subject subject = new Subject(name, teacher);
        Subject subExist = subjectRepository.findByNameAndTeacher(name, teacher);
        if (subExist == null && !name.isEmpty()) {
            subjectRepository.save(subject);
            return "Subject added";
        } else if (subExist != null) {
            return "Subject already exists";
        } else {
            return "Name cannot be empty";

        }
    }
}
