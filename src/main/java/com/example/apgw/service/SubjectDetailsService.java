package com.example.apgw.service;

import com.example.apgw.model.SubjectDetails;
import com.example.apgw.repository.SubjectDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubjectDetailsService {

    private final SubjectDetailsRepository repository;

    @Autowired
    public SubjectDetailsService(SubjectDetailsRepository repository) {
        this.repository = repository;
    }

    public List<SubjectDetails> getAllDept() {
        return repository.findAllByOrderByDeptAsc();
    }

    public List<SubjectDetails> getAllYear(String dept) {
        return repository.findAllByDeptOrderByYearAsc(dept);
    }

    public List<SubjectDetails> getAllName(String dept, String year) {
        return repository.findAllByDeptAndYearOrderByNameAsc(dept, year);
    }

    public SubjectDetails findOne(Long id) {
        return repository.findOne(id);
    }
}
