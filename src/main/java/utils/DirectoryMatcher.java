package utils;

import static utils.Constant.ARTICLE_HTML;
import static utils.Constant.ARTICLE_PATH;
import static utils.Constant.AUTHORIZED_BASE_PATH;
import static utils.Constant.BASE_DIRECTORY_PATH;
import static utils.Constant.BASE_HTML;
import static utils.Constant.BASE_PATH;
import static utils.Constant.COMMENT_HTML;
import static utils.Constant.COMMENT_PATH;
import static utils.Constant.EMPTY_ARTICLE_HTML;
import static utils.Constant.EMPTY_ARTICLE_PATH;
import static utils.Constant.LOGIN_FAILED_PATH;
import static utils.Constant.LOGIN_FAIL_HTML;
import static utils.Constant.LOGIN_HTML;
import static utils.Constant.LOGIN_PATH;
import static utils.Constant.REGISTRATION_HTML;
import static utils.Constant.REGISTRATION_PATH;

/**
 * 디렉토리 경로를 매칭하는 유틸리티 클래스
 */
public class DirectoryMatcher {
    /**
     * 주어진 베이스 경로에 대한 디렉토리 경로를 반환한다.
     * @param basePath 베이스 경로
     * @return 해당하는 디렉토리 경로
     */
    public static String matchDirectory(String basePath) {
        if (basePath.equals(BASE_PATH)) {
            return BASE_DIRECTORY_PATH + BASE_HTML;
        } else if (basePath.equals(AUTHORIZED_BASE_PATH)) {
            return BASE_DIRECTORY_PATH + AUTHORIZED_BASE_PATH + BASE_HTML;
        } else if (basePath.equals(LOGIN_PATH)) {
            return BASE_DIRECTORY_PATH + LOGIN_PATH + LOGIN_HTML;
        } else if (basePath.equals(LOGIN_FAILED_PATH)) {
            return BASE_DIRECTORY_PATH + LOGIN_PATH + LOGIN_FAIL_HTML;
        } else if (basePath.equals(REGISTRATION_PATH)) {
            return BASE_DIRECTORY_PATH + REGISTRATION_PATH + REGISTRATION_HTML;
        } else if (basePath.equals(ARTICLE_PATH)) {
            return BASE_DIRECTORY_PATH + ARTICLE_PATH + ARTICLE_HTML;
        } else if (basePath.equals(COMMENT_PATH)) {
            return BASE_DIRECTORY_PATH + COMMENT_PATH + COMMENT_HTML;
        } else if (basePath.equals(EMPTY_ARTICLE_PATH)) {
            return BASE_DIRECTORY_PATH + ARTICLE_PATH + EMPTY_ARTICLE_HTML;
        }
        return BASE_DIRECTORY_PATH + basePath;
    }
}
