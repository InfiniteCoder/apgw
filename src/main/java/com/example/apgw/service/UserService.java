package com.example.apgw.service;

import com.example.apgw.helper.User;
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

    /**
     * Constructor for userService.
     *
     * @param studentRepository Repository for student.
     * @param teacherRepository Repository for teacher.
     */
    @Autowired
    public UserService(StudentRepository studentRepository,
                       TeacherRepository teacherRepository) {
        this.studentRepository = studentRepository;
        this.teacherRepository = teacherRepository;
    }

    /**
     * Get type of user as "teacher", "student" or "new".
     *
     * @return String.
     */
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

    /**
     * Get email id of user.
     *
     * @return email id.
     */
    public String getEmail() {
        OAuth2Authentication authentication =
                (OAuth2Authentication) SecurityContextHolder.getContext().getAuthentication();
        LinkedHashMap<String, String> properties =
                (LinkedHashMap<String, String>) authentication.getUserAuthentication().getDetails();
        return properties.get("email");

    }

    /**
     * Get name of user.
     * @return name.
     */
    public String getName() {
        OAuth2Authentication authentication =
                (OAuth2Authentication) SecurityContextHolder.getContext().getAuthentication();
        LinkedHashMap<String, String> properties =
                (LinkedHashMap<String, String>) authentication.getUserAuthentication().getDetails();
        return properties.get("name");
    }

    /**
     * Get picture of user.
     * @return picture URL.
     */
    public String getPicture() {
        OAuth2Authentication authentication =
                (OAuth2Authentication) SecurityContextHolder.getContext().getAuthentication();
        LinkedHashMap<String, String> properties =
                (LinkedHashMap<String, String>) authentication.getUserAuthentication().getDetails();
        return properties.get("picture");
    }

    /**
     * Create new user.
     * @return new User object.
     */
    public User user() {
        return new User(getEmail(), getPicture(), getName());
    }
}
