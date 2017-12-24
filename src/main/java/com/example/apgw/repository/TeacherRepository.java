package com.example.apgw.repository;

import com.example.apgw.model.Teacher;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;

@Transactional
public interface TeacherRepository extends CrudRepository<Teacher, String> {
}
