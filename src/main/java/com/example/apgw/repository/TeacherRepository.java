package com.example.apgw.repository;

import com.example.apgw.model.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

@org.springframework.transaction.annotation.Transactional
public interface TeacherRepository extends JpaRepository<Teacher, String> {
}
