package com.example.apgw.service;

import com.example.apgw.model.StudentSubject;
import com.opencsv.CSVReader;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class StudentCSVParser {
    public static ArrayList<StudentSubject> parse(MultipartFile file, Long subjectId) throws IOException {
        ArrayList<StudentSubject> list = new ArrayList<>();
        CSVReader reader;
        try {
            reader = new CSVReader(new InputStreamReader(new ByteArrayInputStream(file.getBytes())));
            String[] line;
            while ((line = reader.readNext()) != null) {
                String uid = line[0];
                System.out.println(uid);
                String studentEmail = line[1];
                System.out.println(studentEmail);
                StudentSubject studentSubject = new StudentSubject(subjectId, studentEmail, uid);
                list.add(studentSubject);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        }
        return list;
    }
}
