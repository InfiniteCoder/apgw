package com.example.apgw.helper;

import com.example.apgw.model.Assignment;
import com.example.apgw.model.Submission;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.FileSystemException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

public class FileCopyHelper {

    private String basedir;

    public FileCopyHelper(String basedir) {
        this.basedir = basedir;
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
        createDir(path);
        //copy files to temp
        copyFilesToTemp(submission, assignment, path);
        //check type and copy appropriate script
        copyScriptToTemp(submission, path);
    }

    private void copyScriptToTemp(Submission submission, Path path) throws IOException, URISyntaxException {
        String type = getCodeType(submission);
        String scriptName;
        switch (type) {
            case "c":
                scriptName = "c-script.sh";
                break;
            case "cpp":
                scriptName = "cpp-script.sh";
                break;
            default:
                scriptName = "";
                break;
        }

        Files.copy(Paths.get(FileStorageHelper.class.getResource("/" + scriptName).toURI()),
                path.resolve(scriptName),
                REPLACE_EXISTING);
        Boolean isExecutable = path.resolve(scriptName).toFile().setExecutable(true);
        if (!isExecutable) {
            throw new FileSystemException("cannot change permission");
        }
    }

    private void copyFilesToTemp(Submission submission, Assignment assignment, Path path) throws IOException {
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
    }

    private void createDir(Path path) throws FileSystemException {
        if (!path.toFile().exists()) {
            boolean mkdir = path.toFile().mkdirs();
            if (!mkdir) {
                throw new FileSystemException("Cannot create dir");
            }
        }
    }

    /**
     * Get the source code type of a submission.
     *
     * @param submission Submission to be checked.
     * @return type, valid options are "c", "cpp" and "invalid".
     */
    public String getCodeType(Submission submission) {
        File codeDir = new File(basedir + "/apgw/submission/" + submission.getId());
        File codeFile = Objects.requireNonNull(codeDir.listFiles())[0];
        String type = FilenameUtils.getExtension(codeFile.getName());
        if (type.equals("c")) {
            return "c";
        }

        //c++ types
        Set<String> cppSuffixes =
                new HashSet<>(Arrays.asList("cc", "cp", "cxx", "cpp", "CPP", "c++", "C"));
        if (cppSuffixes.contains(type)) {
            return "cpp";
        }
        return "invalid";
    }
}
