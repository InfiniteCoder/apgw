package com.example.apgw.repository;

import com.example.apgw.model.SubjectDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface SubjectDetailsRepository extends JpaRepository<SubjectDetails, Long> {
    List<SubjectDetails> findAllByOrderByDeptAsc();

    List<SubjectDetails> findAllByDeptOrderByYearAsc(String dept);

    List<SubjectDetails> findAllByDeptAndYearOrderByNameAsc(String dept, String Year);
}
