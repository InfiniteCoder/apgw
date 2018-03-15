package com.example.apgw.helper;

import com.example.apgw.model.Assignment;
import com.example.apgw.model.Submission;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.FileSystemException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

public class FileStorageHelper {

    private String basedir;

    public FileStorageHelper(String basedir) {
        this.basedir = basedir;
    }

    /**
     * Store files of assignment on disk.
     *
     * @param id           id of assignment.
     * @param inputFile    file containing input.
     * @param outputFile   file containing output.
     * @param questionFile file containing question.
     */
    public void storeAssignmentFiles(Long id,
                                     MultipartFile inputFile,
                                     MultipartFile outputFile,
                                     MultipartFile questionFile)
            throws IOException {
        //set path
        String path = basedir + "/apgw/assi/" + id + "/";

        if (!new File(path).exists()) {
            boolean mkdir = new File(path).mkdirs();
            if (!mkdir) {
                throw new FileSystemException("cannot create directory");
            }
        }

        //Save files
        String inputPath = path + "input";
        String outputPath = path + "output";
        String questionPath = path + "question";
        File inputDest = new File(inputPath);
        File outputDest = new File(outputPath);
        File questionDest = new File(questionPath);

        inputFile.transferTo(inputDest);
        outputFile.transferTo(outputDest);
        questionFile.transferTo(questionDest);
    }

    /**
     * Copy files to temp directory.
     *
     * @param submission submission whose files are to be copied.
     * @param assignment assignment whose files are to be copied.
     * @param path       path of tempdir.
     * @throws IOException        If I/O fails.
     * @throws URISyntaxException If path syntax is incorrect.
     */
    public void copyFiles(Submission submission, Assignment assignment, Path path)
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
        Files.copy(Paths.get(FileStorageHelper.class.getResource("/c-script.sh").toURI()),
                path.resolve("c-script.sh"),
                REPLACE_EXISTING);
        Boolean isExecutable = path.resolve("c-script.sh").toFile().setExecutable(true);
        if (!isExecutable) {
            throw new FileSystemException("cannot change permission");
        }
    }

    public void storeSubmissionFiles(Submission submission, MultipartFile file)
            throws IOException {
        //set path
        String path = basedir + "/apgw/submission/" + submission.getId() + "/";
        if (!new File(path).exists()) {
            boolean mkdir = new File(path).mkdirs();
            if (!mkdir) {
                throw new FileSystemException("Cannot create dir");
            }
        }

        //Save files
        String submissionPath = path + file.getOriginalFilename();
        File dest = new File(submissionPath);
        file.transferTo(dest);
    }
}
