package com.example.apgw.repository;

import com.example.apgw.model.Assignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface AssignmentRepository extends JpaRepository<Assignment, Long> {
}
