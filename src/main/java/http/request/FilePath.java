package http.request;

import http.handler.CommandMatcher;
import java.io.File;
import java.io.FileNotFoundException;
import utils.DirectoryMatcher;

public class FilePath {
    public static final String IS_INVALID_FILE_PATH = "올바른 파일이 아닙니다.";
    private String filePath;
    private String filePathUrl;

    public FilePath(String file) throws FileNotFoundException {
        this.filePath = file.trim();
        String absoluteFilePathUrl = DirectoryMatcher.matchUnknownEndPoint(filePath);
        if (!isValidFilePath(absoluteFilePathUrl) && !CommandMatcher.isValidCommand(filePath)) {
            throw new FileNotFoundException(IS_INVALID_FILE_PATH);
        }
        this.filePathUrl = setFilePath(filePath);
    }

    private String setFilePath(String filePathUrl) {
        String absoluteFilePathUrl = DirectoryMatcher.matchUnknownEndPoint(filePathUrl);
        if (isValidFilePath(absoluteFilePathUrl)) {
            return absoluteFilePathUrl;
        }
        return filePathUrl;
    }

    public boolean isValidFilePath(String filePath) {
        File file = new File(filePath);
        return file.isFile() && file.exists();
    }

    public void setFilePathUrl(String filePathUrl) {
        this.filePathUrl = filePathUrl;
    }

    public String getFilePath() {
        return filePath;
    }

    public String getFilePathUrl() {
        return filePathUrl;
    }

    public File makeFile() {
        return new File(filePathUrl);
    }
}
