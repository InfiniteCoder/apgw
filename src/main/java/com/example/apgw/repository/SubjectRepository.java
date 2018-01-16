package com.example.apgw.repository;

import com.example.apgw.model.Subject;
import com.example.apgw.model.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubjectRepository extends JpaRepository<Subject, Long> {
    Subject findByNameAndTeacher(String name, Teacher teacher);
}
