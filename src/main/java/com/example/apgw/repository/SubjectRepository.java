package com.example.apgw.repository;

import com.example.apgw.model.Subject;
import com.example.apgw.model.Teacher;
import org.springframework.data.repository.CrudRepository;

public interface SubjectRepository extends CrudRepository<Subject, Long> {
    Subject findByTeacherAndName(Teacher teacher, String name);
}
