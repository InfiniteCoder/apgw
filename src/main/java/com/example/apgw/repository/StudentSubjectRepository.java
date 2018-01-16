package com.example.apgw.repository;

import com.example.apgw.model.StudentSubject;
import com.example.apgw.model.StudentSubjectId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentSubjectRepository extends JpaRepository<StudentSubject, StudentSubjectId> {
}
