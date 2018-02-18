package com.example.apgw.service;

import com.example.apgw.model.Assignment;
import com.example.apgw.model.Subject;
import com.example.apgw.model.Teacher;
import com.example.apgw.repository.AssignmentRepository;
import com.example.apgw.repository.SubjectRepository;
import com.example.apgw.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.security.Principal;
import java.util.List;

@Service
public class AssignmentService {

    private final AssignmentRepository assignmentRepository;
    private final SubjectRepository subjectRepository;
    private final TeacherRepository teacherRepository;
    private final UserService userService;
    @Value("${file-path}")
    String basedir;

    @Autowired
    public AssignmentService(AssignmentRepository assignmentRepository,
                             SubjectRepository subjectRepository,
                             TeacherRepository teacherRepository,
                             UserService userService) {
        this.assignmentRepository = assignmentRepository;
        this.subjectRepository = subjectRepository;
        this.teacherRepository = teacherRepository;
        this.userService = userService;
    }

    public String addAssignment(Principal principal,
                                String subjectName,
                                String title,
                                MultipartFile inputFile,
                                MultipartFile outputFile,
                                MultipartFile questionFile) {

        // find relevant subject
        String teacherEmail = userService.getEmail(principal);
        Teacher teacher = teacherRepository.findOne(teacherEmail);
        Subject subject = subjectRepository.findByNameAndTeacher(subjectName, teacher);

        // check title and files are not empty
        if (title.isEmpty()) {
            return "Empty title";
        }
        if (inputFile.isEmpty() || outputFile.isEmpty() || questionFile.isEmpty()) {
            return "Empty file";
        }

        // create Assignment with only subject to get id
        Assignment assignment = assignmentRepository.save(new Assignment(subject));
        Long id = assignment.getId();

        //set path

        String path = basedir + "/apgw/" + id + "/";
        if (!new File(path).exists()) {
            boolean mkdir = new File(path).mkdirs();
            if (!mkdir) {
                return "Error creating dir";
            }
        }
        //Save files
        String inputPath = path + inputFile.getOriginalFilename();
        String outputPath = path + outputFile.getOriginalFilename();
        String questionPath = path + questionFile.getOriginalFilename();
        File inputDest = new File(inputPath);
        File outputDest = new File(outputPath);
        File questionDest = new File(questionPath);

        try {
            inputFile.transferTo(inputDest);
            outputFile.transferTo(outputDest);
            questionFile.transferTo(questionDest);
        } catch (IOException e) {
            //if failed, update db
            e.printStackTrace();
            assignmentRepository.delete(id);
            return "FS error";
        }

        //update db
        assignment.setTitle(title);
        assignment.setInputPath(inputPath);
        assignment.setOutputPath(outputPath);
        assignment.setQuestionPath(questionPath);
        assignmentRepository.save(assignment);
        return "created";
    }

    public List<Assignment> getAssignments(Principal principal,
                                           String subjectName) {

        //get subject
        String teacherEmail = userService.getEmail(principal);
        Teacher teacher = teacherRepository.findOne(teacherEmail);
        Subject subject = subjectRepository.findByNameAndTeacher(subjectName, teacher);

        return subject.getAssignments();
    }
}
