package com.example.apgw.service;

import com.example.apgw.model.Student;
import com.example.apgw.model.Teacher;
import com.example.apgw.repository.StudentRepository;
import com.example.apgw.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;

@SuppressWarnings("unchecked")
@Service
public class UserService {
    private final StudentRepository studentRepository;
    private final TeacherRepository teacherRepository;

    @Autowired
    public UserService(StudentRepository studentRepository, TeacherRepository teacherRepository) {
        this.studentRepository = studentRepository;
        this.teacherRepository = teacherRepository;
    }

    public String getType() {
        String email = getEmail();

        //check if student
        Student student = studentRepository.findOne(email);
        if (student != null) {
            return "student";
        }
        Teacher teacher = teacherRepository.findOne(email);
        if (teacher != null) {
            return "teacher";
        }
        return "new";
    }

    public String getEmail() {
        OAuth2Authentication authentication =
                (OAuth2Authentication) SecurityContextHolder.getContext().getAuthentication();
        LinkedHashMap<String, String> properties =
                (LinkedHashMap<String, String>) authentication.getUserAuthentication().getDetails();
        return properties.get("email");

    }

    public String getName() {
        OAuth2Authentication authentication =
                (OAuth2Authentication) SecurityContextHolder.getContext().getAuthentication();
        LinkedHashMap<String, String> properties =
                (LinkedHashMap<String, String>) authentication.getUserAuthentication().getDetails();
        return properties.get("name");
    }

    public String getPicture() {
        OAuth2Authentication authentication =
                (OAuth2Authentication) SecurityContextHolder.getContext().getAuthentication();
        LinkedHashMap<String, String> properties =
                (LinkedHashMap<String, String>) authentication.getUserAuthentication().getDetails();
        return properties.get("picture");
    }
}
