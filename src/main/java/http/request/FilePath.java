package http.request;

import http.handler.UrlMapper;
import java.io.File;
import java.io.FileNotFoundException;
import utils.Decoder;
import utils.DirectoryMatcher;

/**
 * http request의 Path를 검증하고 저장하는 클래스
 */
public class FilePath {
    public static final String IS_INVALID_FILE_PATH = "올바른 파일이 아닙니다.";
    private String filePath;

    /**
     * FilePath 클래스의 생성자
     *
     * @param file http request의 Path
     * @throws FileNotFoundException 파일이 존재하지 않거나 올바른 path 요청이 아닌 경우 예외가 발생한다.
     */
    public FilePath(String file) throws FileNotFoundException {
        this.filePath = file.trim();
        String absoluteFilePathUrl = DirectoryMatcher.matchDirectory(filePath);
        if (!UrlMapper.isValidCommand(filePath) && !isValidFilePath(absoluteFilePathUrl)) {
            throw new FileNotFoundException(IS_INVALID_FILE_PATH);
        }
    }

    /**
     * 주어진 path가 유효한 파일 경로인지 검사한다.
     *
     * @param filePath 파일의 전체 경로
     * @return 파일이 존재하고 유효한 경우 true를 반환한다.
     */
    public boolean isValidFilePath(String filePath) {
        filePath = Decoder.decodeStr(filePath);
        File file = new File(filePath);
        return file.isFile() && file.exists();
    }

    /**
     * http request의 Path를 반환한다.
     *
     * @return http request의 Path
     */
    public String getFilePath() {
        return filePath;
    }
}
