package com.example.apgw.helper;

import com.example.apgw.model.Assignment;
import com.example.apgw.model.Submission;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.nio.file.Path;

public class GradingHelper {

    private String basedir;

    public GradingHelper(String basedir) {
        this.basedir = basedir;
    }

    /**
     * Tests the submitted code against test cases.
     *
     * @param submission submission to be tested.
     * @param assignment assignment related to submission.
     * @param tempPath   Path of temp dir.
     * @throws IOException          If parsing result fails.
     * @throws InterruptedException If process fails.
     * @throws URISyntaxException   If file copying fails.
     */
    public void testSubmission(Submission submission,
                               Assignment assignment,
                               Path tempPath)
            throws IOException, InterruptedException, URISyntaxException {
        FileStorageHelper fileStorageHelper = new FileStorageHelper(basedir);

        //copy files
        try {
            fileStorageHelper.copyFiles(submission, assignment, tempPath);
        } catch (URISyntaxException e) {
            e.printStackTrace();
            throw e;
        }

        //run docker
        String dockerCommand = "";
        String type = fileStorageHelper.getCodeType(submission);
        switch (type) {
            case "c":
                dockerCommand = "docker run --rm -v"
                        + tempPath + "/:/home/files/ -w /home/files gcc:7.3 ./c-script.sh";
                break;
            case "cpp":
                dockerCommand = "docker run -e CodeFileExt="
                        + fileStorageHelper.getExtention(submission)
                        + " --rm -v"
                        + tempPath + "/:/home/files/ -w /home/files gcc:7.3 ./cpp-script.sh";
                break;
        }
        Process process = Runtime.getRuntime().exec(dockerCommand);
        process.waitFor();
        InputStreamReader isReader = new InputStreamReader(process.getInputStream());
        String line = new BufferedReader(isReader).readLine();
        int marks = 0;
        try {
            marks = Integer.parseInt(line);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //update marks
        submission.setMarks(marks);

        //delete files from temp
        fileStorageHelper.deleteTemp(tempPath);
    }
}
