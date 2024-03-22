package http.request;

import http.handler.CommandMatcher;
import java.io.File;
import java.io.FileNotFoundException;
import utils.DirectoryMatcher;

public class FilePath {
    public static final String IS_INVALID_FILE_PATH = "올바른 파일이 아닙니다.";
    private String filePathUrl;

    public FilePath(String filePathUrl) throws FileNotFoundException {
        filePathUrl = filePathUrl.trim();
        String absoluteFilePathUrl = DirectoryMatcher.mathDirectory(filePathUrl);
        if (!isValidFilePath(absoluteFilePathUrl) && !CommandMatcher.isValidCommand(filePathUrl)) {
            throw new FileNotFoundException(IS_INVALID_FILE_PATH);
        }
        this.filePathUrl = setFilePath(filePathUrl);
    }

    private String setFilePath(String filePathUrl) {
        String absoluteFilePathUrl = DirectoryMatcher.mathDirectory(filePathUrl);
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

    public String getFilePathUrl() {
        return filePathUrl;
    }

    public File makeFile() {
        return new File(filePathUrl);
    }
}
