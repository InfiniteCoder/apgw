package com.example.apgw.controller;

import com.example.apgw.model.Assignment;
import com.example.apgw.model.Submission;
import com.example.apgw.service.AssignmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.Files;
import java.security.acl.NotOwnerException;
import java.util.List;

@RestController
public class AssignmentController {

    private final AssignmentService service;

    @Autowired
    public AssignmentController(AssignmentService service) {
        this.service = service;
    }

    /**
     * Add Assignment to the subject.
     *
     * @param subjectName  Name of subject to which assignment is to be added.
     * @param title        Title fo assignment.
     * @param inputFile    File containing input test cases.
     * @param outputFile   File containing output test cases.
     * @param questionFile File containing question details.
     * @return Error/success message with HttpStatus.
     */
    @PostMapping("/api/addAssignment")
    public ResponseEntity<String> addAssignment(
            @RequestParam(name = "subjectName") String subjectName,
            @RequestParam(name = "title") String title,
            @RequestParam(name = "inputFile") MultipartFile inputFile,
            @RequestParam(name = "outputFile") MultipartFile outputFile,
            @RequestParam(name = "questionFile") MultipartFile questionFile) {

        try {
            service.addAssignment(subjectName, title, inputFile, outputFile, questionFile);
            return new ResponseEntity<>("created", HttpStatus.CREATED);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>("Not Modified", HttpStatus.NOT_MODIFIED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Empty Files", HttpStatus.NO_CONTENT);
        }
    }

    /**
     * List all assignments, search by subjects.
     *
     * @param subjectName name of subject.
     * @return List of assignments.
     */
    @GetMapping("/api/assignments")
    public ResponseEntity<List<Assignment>> getAssignments(
            @RequestParam(name = "subjectName") String subjectName) {
        List<Assignment> list;
        try {
            list = service.getAssignments(subjectName);
            return new ResponseEntity<>(list, HttpStatus.OK);
        } catch (NotOwnerException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * List all assignments, search by Id.
     *
     * @param subjectId Id of subject.
     * @return List of assignment.
     */
    @GetMapping("/api/assignmentsById")
    public ResponseEntity<List<Assignment>> getAssignmentsById(Long subjectId) {
        List<Assignment> list;
        try {
            list = service.getAssignmentsById(subjectId);
            return new ResponseEntity<>(list, HttpStatus.OK);
        } catch (NotOwnerException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    /**
     * Grade all submissions for an assignment.
     *
     * @param assignmentId Id of assignment to grade.
     * @return Error/success message with HttpStatus.
     */
    @PostMapping("/api/grade")
    public ResponseEntity<String> grade(@RequestParam(name = "id") Long assignmentId) {
        try {
            service.grade(assignmentId);
            return new ResponseEntity<>("grading completed", HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_MODIFIED);
        }
    }

    /**
     * Get an assignment.
     *
     * @param id id of assignment to retrieve.
     * @return Assignment.
     */
    @GetMapping("/api/getAssignment")
    public ResponseEntity<Assignment> getAssignment(Long id) {
        return new ResponseEntity<>(service.getAssignment(id), HttpStatus.OK);
    }

    /**
     * get list of submissions.
     *
     * @param assignmentId get id of assignment.
     * @return list of submission.
     */
    @GetMapping("/api/submissions")
    public ResponseEntity<List<Submission>> submission(
            @RequestParam(name = "id") Long assignmentId) {

        List<Submission> list = service.getSubmissions(assignmentId);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    /**
     * Returns question file.
     *
     * @param id       id of assignment.
     * @param response HttpServletResponse.
     */
    @GetMapping(value = "/api/questionFile", produces = "text/plain")
    public void getImageAsResource(Long id, HttpServletResponse response) {
        try {
            Files.copy(service.getQuestionPath(id),
                    response.getOutputStream());
            response.setCharacterEncoding("UTF-8");
            response.flushBuffer();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
