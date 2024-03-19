package http.request;

import static utils.Constant.IS_INVALID_FILE_PATH;
import static utils.Constant.USER_CREATE_COMMAND;
import static utils.Constant.USER_LOGIN_COMMAND;

import java.io.File;
import java.io.FileNotFoundException;
import utils.DirectoryMatcher;

public class FilePath {
    private final String originalFilePath;
    private final String filePath;

    public FilePath(String filePath) throws FileNotFoundException {
        this.originalFilePath = filePath;
        this.filePath = DirectoryMatcher.mathDirectory(filePath);
        if (!isValidFilePath() && !isUserCreateCommand() && !isLoginCommand()) {
            throw new FileNotFoundException(IS_INVALID_FILE_PATH);
        }
    }

    private boolean isValidFilePath() {
        File file = new File(filePath);
        return file.isFile() && file.exists();
    }

    public boolean isUserCreateCommand() {
        return originalFilePath.equals(USER_CREATE_COMMAND);
    }

    public boolean isLoginCommand() {
        return originalFilePath.equals(USER_LOGIN_COMMAND);
    }

    public String getOriginalFilePath() {
        return originalFilePath;
    }

    public String getFilePath() {
        return filePath;
    }
}
