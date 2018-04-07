package com.example.apgw.repository;

import com.example.apgw.model.Subject;
import com.example.apgw.model.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface SubjectRepository extends JpaRepository<Subject, Long> {
    Subject findByDetails_NameAndTeacher(String name, Teacher teacher);

    Subject findByDetails_IdAndTeacher(Long Id, Teacher teacher);
}
