package com.example.apgw.helper;

import com.example.apgw.model.StudentSubject;
import com.opencsv.CSVReader;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class StudentCsvParser {
    /**
     * Parses the CSV file and returns an ArrayList of StudentSubject,
     * which contains the provided students and subject.
     *
     * @param file      CSV file in the format student-uid,student-email.
     *                  DON'T include heading line.
     * @param subjectId The id of subject to which students are to be added.
     * @return ArrayList of StudentSubject containing data from CSV file, and the subjectid.
     * @throws IOException If the CSV file is malformed.
     */
    public static ArrayList<StudentSubject> parse(MultipartFile file,
                                                  Long subjectId) throws IOException {
        ArrayList<StudentSubject> list = new ArrayList<>();
        CSVReader reader;
        try {
            byte[] bytes = file.getBytes();
            reader = new CSVReader(new InputStreamReader(new ByteArrayInputStream(bytes)));
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
