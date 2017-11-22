package com.example.apgw.repository;

import com.example.apgw.model.Student;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;


@Transactional
public interface StudentRepository extends CrudRepository<Student, String> {

}
