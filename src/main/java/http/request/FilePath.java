package http.request;

import http.handler.CommandMatcher;
import java.io.File;
import java.io.FileNotFoundException;
import utils.Decoder;
import utils.DirectoryMatcher;

public class FilePath {
    public static final String IS_INVALID_FILE_PATH = "올바른 파일이 아닙니다.";
    private String filePath;
    private String filePathUrl;

    public FilePath(String file) throws FileNotFoundException {
        this.filePath = file.trim();
        String absoluteFilePathUrl = DirectoryMatcher.matchDirectory(filePath);
        if (!CommandMatcher.isValidCommand(filePath) && !isValidFilePath(absoluteFilePathUrl)) {
            throw new FileNotFoundException(IS_INVALID_FILE_PATH);
        }
        this.filePathUrl = filePath;
    }

    public boolean isValidFilePath(String filePath) {
        filePath = Decoder.decodeStr(filePath);
        File file = new File(filePath);
        return file.isFile() && file.exists();
    }

    public String getFilePath() {
        return filePath;
    }

    public String getFilePathUrl() {
        return filePathUrl;
    }
}
