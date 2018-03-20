package com.example.apgw.service;

import com.example.apgw.model.Subject;
import com.example.apgw.model.SubjectDetails;
import com.example.apgw.model.Teacher;
import com.example.apgw.repository.SubjectRepository;
import com.example.apgw.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.acl.NotOwnerException;

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
    public String addSubject(String name, String year, String dept) {
        Teacher teacher = teacherRepository.findOne(userService.getEmail());
        SubjectDetails details = new SubjectDetails(name, year, dept);
        Subject subject = new Subject(details);
        Subject subExist = subjectRepository.findByDetails_NameAndTeacher(name, teacher);
        if (subExist == null && !name.isEmpty()) {
            subjectRepository.save(subject);
            return "Subject added";
        } else if (subExist != null) {
            return "Subject already exists";
        } else {
            return "Name cannot be empty";

        }
    }

    /**
     * delete a subject.
     *
     * @param id id of subject to be deleted.
     * @throws NotOwnerException if current user is not the owner of this subject.
     */
    public void deleteSubject(Long id) throws NotOwnerException {
        Subject subject = subjectRepository.findOne(id);
        //delete only if current user is owner of this subject
        if (subject.getTeacher().getEmail().equals(userService.getEmail())) {
            subjectRepository.delete(id);
        } else {
            throw new NotOwnerException();
        }
    }
}
