package com.example.apgw.service;

import com.example.apgw.helper.FileStorageHelper;
import com.example.apgw.model.Assignment;
import com.example.apgw.model.Student;
import com.example.apgw.model.StudentSubject;
import com.example.apgw.model.Submission;
import com.example.apgw.repository.AssignmentRepository;
import com.example.apgw.repository.SubmissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.acl.NotOwnerException;

@Service
public class SubmissionService {

    private final AssignmentRepository assignmentRepository;
    private final UserService userService;
    private final SubmissionRepository submissionRepository;
    @Value("${file-path}")
    String basedir;

    /**
     * Constructor for Submission service.
     *
     * @param assignmentRepository     Repository for Assignment.
     * @param userService              Repository for UserService.
     * @param submissionRepository     Repository for SubmissionRepository.
     */
    @Autowired
    public SubmissionService(AssignmentRepository assignmentRepository,
                             UserService userService,
                             SubmissionRepository submissionRepository) {
        this.assignmentRepository = assignmentRepository;
        this.userService = userService;
        this.submissionRepository = submissionRepository;
    }

    /**
     * Add a submission.
     * @param assignmentId id of assignment.
     * @param file         submitted file.
     */
    public void addSubmission(Long assignmentId, MultipartFile file)
            throws Exception {
        //find Assignment
        Assignment assignment = assignmentRepository.findOne(assignmentId);

        //check student is allowed to upload
        Student student = assignment.getSubject().getStudents().stream()
                .filter(this::isCurrentStudent)
                .findFirst()
                .orElseThrow(NotOwnerException::new)
                .getStudent();

        //check if student has already uploaded
        boolean firstUpload = assignment.getSubmissions().stream()
                .noneMatch(submission -> submission.getStudent().equals(student));
        if (!firstUpload) {
            throw new Exception("already uploaded assignment");
        }

        //Create submission
        Submission submissionTemp = new Submission(assignment, student);
        Submission submission = submissionRepository.save(submissionTemp);

        try {
            new FileStorageHelper(basedir)
                    .storeSubmissionFiles(submission, file);
        } catch (IOException e) {
            e.printStackTrace();
            submissionRepository.delete(submission.getId());
            throw e;
        }
    }


    private boolean isCurrentStudent(StudentSubject studentSubject) {
        String email = userService.getEmail();
        return studentSubject.getStudentEmail().equals(email);
    }
}
