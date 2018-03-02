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

    public List<Assignment> getAssignments(String subjectName) {

        //get subject
        String teacherEmail = userService.getEmail();
        Teacher teacher = teacherRepository.findOne(teacherEmail);
        Subject subject = subjectRepository.findByNameAndTeacher(subjectName, teacher);

        return subject.getAssignments();
    }

    public List<Assignment> getAssignmentsById(Long id) {
        //Todo: Check student is associated to the subject
        //return
        Subject subject = subjectRepository.findOne(id);
        return subject.getAssignments();
    }

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
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            line = reader.readLine();
            int marks = Integer.parseInt(line);

            //update marks
            submission.setMarks(marks);
            submissionRepository.save(submission);
        }
    }

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
        Path uploadPath = Paths.get(submission.getUploadPath());
        Path inputPath = Paths.get(assignment.getInputPath());
        Path outputPath = Paths.get(assignment.getOutputPath());
        Files.copy(uploadPath, path.resolve(uploadPath.getFileName()), REPLACE_EXISTING);
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
}
