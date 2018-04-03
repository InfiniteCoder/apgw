package com.example.apgw.helper;

import com.example.apgw.model.Submission;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystemException;
import java.nio.file.Path;
import java.util.Objects;

public class FileStorageHelper {

    private String basedir;

    /**
     * Constructor for FileStorageHelper.
     *
     * @param basedir base directory where files are stored.
     */
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
     * Stores submission files to disk.
     *
     * @param submission submission to be stored.
     * @param file       File to be stored.
     * @throws IOException If error occurs on storage.
     */
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

    /**
     * clean temp dir.
     *
     * @param tempPath path to temp dir.
     * @throws IOException If deletion fails.
     */
    public void deleteTemp(Path tempPath) throws IOException {
        //set tempPath
        FileUtils.cleanDirectory(tempPath.toFile());
    }

    /**
     * Get extension of a submitted code.
     *
     * @param submission submission to be checked.
     * @return Extension of a code.
     */
    public String getExtension(Submission submission) {
        File codeDir = new File(basedir + "/apgw/submission/" + submission.getId());
        File codeFile = Objects.requireNonNull(codeDir.listFiles())[0];
        return FilenameUtils.getExtension(codeFile.getName());
    }
}
