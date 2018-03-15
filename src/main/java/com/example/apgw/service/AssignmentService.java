package com.example.apgw.service;

import com.example.apgw.helper.FileStorageHelper;
import com.example.apgw.model.Assignment;
import com.example.apgw.model.Subject;
import com.example.apgw.model.Submission;
import com.example.apgw.model.Teacher;
import com.example.apgw.repository.AssignmentRepository;
import com.example.apgw.repository.SubjectRepository;
import com.example.apgw.repository.SubmissionRepository;
import com.example.apgw.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.FileSystemException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.acl.NotOwnerException;
import java.util.List;

@Service
public class AssignmentService {

    private final AssignmentRepository assignmentRepository;
    private final SubjectRepository subjectRepository;
    private final TeacherRepository teacherRepository;
    private final UserService userService;
    private final SubmissionRepository submissionRepository;

    @Value("${file-path}")
    String basedir;

    /**
     * Constructor for Assignment Service.
     *
     * @param assignmentRepository Repository for assignment.
     * @param subjectRepository    Repository for subject.
     * @param teacherRepository    Repository for teacher.
     * @param userService          Repository for userService.
     * @param submissionRepository Repository for submission.
     */
    @Autowired
    public AssignmentService(AssignmentRepository assignmentRepository,
                             SubjectRepository subjectRepository,
                             TeacherRepository teacherRepository,
                             UserService userService,
                             SubmissionRepository submissionRepository) {
        this.assignmentRepository = assignmentRepository;
        this.subjectRepository = subjectRepository;
        this.teacherRepository = teacherRepository;
        this.userService = userService;
        this.submissionRepository = submissionRepository;
    }

    /**
     * Add Assignment to the subject.
     *
     * @param subjectName  Name of subject to which assignment is to be added.
     * @param title        Title fo assignment.
     * @param inputFile    File containing input test cases.
     * @param outputFile   File containing output test cases.
     * @param questionFile File containing question details.
     */
    public void addAssignment(String subjectName,
                              String title,
                              MultipartFile inputFile,
                              MultipartFile outputFile,
                              MultipartFile questionFile) throws Exception {

        // find relevant subject
        String teacherEmail = userService.getEmail();
        Teacher teacher = teacherRepository.findOne(teacherEmail);
        Subject subject = subjectRepository.findByNameAndTeacher(subjectName, teacher);

        // check title and files are not empty
        if (title.isEmpty()
                || inputFile.isEmpty()
                || outputFile.isEmpty()
                || questionFile.isEmpty()) {
            throw new Exception("empty input");
        }

        // create Assignment
        Assignment assignment = assignmentRepository.save(new Assignment(subject, title));
        Long id = assignment.getId();

        //try to save files
        try {
            FileStorageHelper.storeAssignmentFiles(id, inputFile, outputFile, questionFile);
        } catch (IOException ex) {
            //delete assignment
            assignmentRepository.delete(id);
            throw ex;
        }
    }

    /**
     * List all assignments, search by subjects.
     * @param subjectName name of subject.
     * @return List of assignments.
     */
    public List<Assignment> getAssignments(String subjectName) throws NotOwnerException {
        //get subject
        String teacherEmail = userService.getEmail();
        Teacher teacher = teacherRepository.findOne(teacherEmail);
        Subject subject = teacher.getSubjects().stream()
                .filter(s -> s.getName().equals(subjectName))
                .findFirst()
                .orElseThrow(NotOwnerException::new);
        return subject.getAssignments();
    }

    /**
     * List all assignments, search by Id.
     * @param id Id of subject.
     * @return List of assignment.
     */
    public List<Assignment> getAssignmentsById(Long id) {
        //Todo: Check student is associated to the subject
        //return
        Subject subject = subjectRepository.findOne(id);
        return subject.getAssignments();
    }

    /**
     * Grade all submissions for an assignment.
     * @param id id of assignment.
     * @throws IOException If reading/writing files fails.
     * @throws InterruptedException If process gets interrupted.
     * @throws NotOwnerException If current user is not the owner of the subject.
     */
    public void grade(Long id) throws IOException, InterruptedException, NotOwnerException {
        //get the Assignment
        Assignment assignment = assignmentRepository.findOne(id);

        //match with Teacher
        String teacherEmail = userService.getEmail();
        if (!assignment.getSubject().getTeacher().getEmail().equals(teacherEmail)) {
            throw new NotOwnerException();
        }

        //get all submissions
        List<Submission> submissions = assignment.getSubmissions();
        Path path = Paths.get(basedir + "/apgw/temp");

        //for every submission
        for (Submission submission : submissions) {
            //copy files
            try {
                FileStorageHelper.copyFiles(submission, assignment, path);
            } catch (Exception e) {
                e.printStackTrace();
                throw new FileSystemException("Cannot create directory");
            }

            //run docker
            Process process = Runtime.getRuntime().exec("docker run --rm -v"
                    + path
                    + "/:/home/files/ -w /home/files gcc:7.3 ./c-script.sh");
            process.waitFor();
            InputStreamReader isReader = new InputStreamReader(process.getInputStream());
            BufferedReader reader = new BufferedReader(isReader);
            String line;
            line = reader.readLine();
            int marks = Integer.parseInt(line);

            //update marks
            submission.setMarks(marks);
            submissionRepository.save(submission);
        }
    }

    /**
     * Get an assignment.
     *
     * @param id id of assignment to retrieve.
     * @return Assignment.
     */
    public Assignment getAssignment(Long id) {
        return assignmentRepository.findOne(id);
    }
}
