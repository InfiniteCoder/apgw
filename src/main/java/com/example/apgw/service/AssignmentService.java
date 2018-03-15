package com.example.apgw.service;

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
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.nio.file.FileSystemException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.acl.NotOwnerException;
import java.util.List;
import java.util.Optional;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

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
     * @return Error/success message.
     */
    public String addAssignment(String subjectName,
                                String title,
                                MultipartFile inputFile,
                                MultipartFile outputFile,
                                MultipartFile questionFile) {

        // find relevant subject
        String teacherEmail = userService.getEmail();
        Teacher teacher = teacherRepository.findOne(teacherEmail);
        Subject subject = subjectRepository.findByNameAndTeacher(subjectName, teacher);

        // check title and files are not empty
        if (title.isEmpty()) {
            return "Empty title";
        }
        if (inputFile.isEmpty() || outputFile.isEmpty() || questionFile.isEmpty()) {
            return "Empty file";
        }

        // create Assignment
        Assignment assignment = assignmentRepository.save(new Assignment(subject, title));
        Long id = assignment.getId();

        //set path
        String path = basedir + "/apgw/assi/" + id + "/";
        if (!new File(path).exists()) {
            boolean mkdir = new File(path).mkdirs();
            if (!mkdir) {
                return "Error creating dir";
            }
        }

        //Save files
        String inputPath = path + "input";
        String outputPath = path + "output";
        String questionPath = path + "question";
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
        return "created";
    }

    /**
     * List all assignments, search by subjects.
     * @param subjectName name of subject.
     * @return List of assignments.
     */
    public List<Assignment> getAssignments(String subjectName) {

        //get subject
        String teacherEmail = userService.getEmail();
        Teacher teacher = teacherRepository.findOne(teacherEmail);
        Subject subject = subjectRepository.findByNameAndTeacher(subjectName, teacher);

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
                copyFiles(submission, assignment, path);
            } catch (Exception e) {
                e.printStackTrace();
                throw new FileSystemException("Cannot create directory");
            }

            //run docker
            Process process = Runtime.getRuntime().exec("docker run --rm -v" + path +
                    "/:/home/files/ -w /home/files gcc:7.3 ./c-script.sh");
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
     * Copy files to temp directory.
     * @param submission submission whose files are to be copied.
     * @param assignment assignment whose files are to be copied.
     * @param path path of tempdir.
     * @throws IOException If I/O fails.
     * @throws URISyntaxException If path syntax is incorrect.
     */
    private void copyFiles(Submission submission, Assignment assignment, Path path)
            throws IOException, URISyntaxException {
        //create dir if does not exist
        if (!path.toFile().exists()) {
            boolean mkdir = path.toFile().mkdirs();
            if (!mkdir) {
                throw new FileSystemException("Cannot create dir");
            }
        }
        //copy files to temp
        Path submissionDirectory = Paths.get(basedir + "/apgw/submission/" + submission.getId());
        Optional<Path> submissionPath = Files.list(submissionDirectory).findFirst();
        Path inputPath = Paths.get(basedir + "/apgw/assi/" + assignment.getId() + "/input");
        Path outputPath = Paths.get(basedir + "/apgw/assi/" + assignment.getId() + "/output");
        if (submissionPath.isPresent()) {
            Files.copy(submissionPath.get(),
                    path.resolve(submissionPath.get().getFileName()),
                    REPLACE_EXISTING);
        }
        Files.copy(inputPath, path.resolve("input"), REPLACE_EXISTING);
        Files.copy(outputPath, path.resolve("output"), REPLACE_EXISTING);
        Files.copy(Paths.get(getClass().getResource("/c-script.sh").toURI()),
                path.resolve("c-script.sh"),
                REPLACE_EXISTING);
        Boolean isExecutable = path.resolve("c-script.sh").toFile().setExecutable(true);
        if (!isExecutable) {
            throw new FileSystemException("cannot change permission");
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
