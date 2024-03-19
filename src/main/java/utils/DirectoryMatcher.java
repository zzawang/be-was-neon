package utils;

import static utils.Constant.ARTICLE_HTML;
import static utils.Constant.ARTICLE_PATH;
import static utils.Constant.BASE_PATH;
import static utils.Constant.BASIC_HTML;
import static utils.Constant.BASIC_PATH;
import static utils.Constant.COMMENT_HTML;
import static utils.Constant.COMMENT_PATH;
import static utils.Constant.LOGIN_FAILED_PATH;
import static utils.Constant.LOGIN_FAIL_HTML;
import static utils.Constant.LOGIN_HTML;
import static utils.Constant.LOGIN_PATH;
import static utils.Constant.REGISTRATION_HTML;
import static utils.Constant.REGISTRATION_PATH;

public class DirectoryMatcher {
    public static String mathDirectory(String filePath) {
        if (filePath.equals(BASIC_PATH) || filePath.equals(BASIC_HTML)) {
            return BASE_PATH + BASIC_HTML;
        }
        if (filePath.equals(ARTICLE_PATH) || filePath.equals(ARTICLE_HTML)) {
            return BASE_PATH + ARTICLE_PATH + ARTICLE_HTML;
        }
        if (filePath.equals(COMMENT_PATH) || filePath.equals(COMMENT_HTML)) {
            return BASE_PATH + COMMENT_PATH + COMMENT_HTML;
        }
        if (filePath.equals(REGISTRATION_PATH) || filePath.equals(REGISTRATION_HTML)) {
            return BASE_PATH + REGISTRATION_PATH + REGISTRATION_HTML;
        }
        if (filePath.equals(LOGIN_PATH) || filePath.endsWith(LOGIN_HTML)) {
            return BASE_PATH + LOGIN_PATH + LOGIN_HTML;
        }
        if (filePath.equals(LOGIN_FAILED_PATH) || filePath.endsWith(LOGIN_FAIL_HTML)) {
            return BASE_PATH + LOGIN_PATH + LOGIN_FAIL_HTML;
        }
        return BASE_PATH + filePath; // 나머지는 기본 경로
    }
}
