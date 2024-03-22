package utils;

import static utils.Constant.ARTICLE_HTML;
import static utils.Constant.ARTICLE_PATH;
import static utils.Constant.AUTHORIZED_BASE_PATH;
import static utils.Constant.BASE_DIRECTORY_PATH;
import static utils.Constant.BASE_HTML;
import static utils.Constant.BASE_PATH;
import static utils.Constant.COMMENT_HTML;
import static utils.Constant.COMMENT_PATH;
import static utils.Constant.LOGIN_FAILED_PATH;
import static utils.Constant.LOGIN_FAIL_HTML;
import static utils.Constant.LOGIN_HTML;
import static utils.Constant.LOGIN_PATH;
import static utils.Constant.REGISTRATION_HTML;
import static utils.Constant.REGISTRATION_PATH;

import http.response.Status;
import java.util.List;

public class DirectoryMatcher {
    public enum AuthorizedEndPoint {
        MAIN(Status.OK, AUTHORIZED_BASE_PATH + BASE_HTML,
                List.of(BASE_PATH, BASE_HTML, AUTHORIZED_BASE_PATH, AUTHORIZED_BASE_PATH + BASE_HTML)),
        REGISTRATION(Status.OK, REGISTRATION_PATH + REGISTRATION_HTML,
                List.of(REGISTRATION_PATH, REGISTRATION_PATH + REGISTRATION_HTML)),
        LOGIN(Status.BAD_REQUEST, AUTHORIZED_BASE_PATH + BASE_HTML, List.of(LOGIN_PATH, LOGIN_PATH + LOGIN_HTML)),
        LOGIN_FAILED(Status.BAD_REQUEST, AUTHORIZED_BASE_PATH + BASE_HTML,
                List.of(LOGIN_FAILED_PATH, LOGIN_FAILED_PATH + LOGIN_FAIL_HTML)),
        ARTICLE(Status.OK, ARTICLE_PATH + ARTICLE_HTML, List.of(ARTICLE_PATH, ARTICLE_PATH + ARTICLE_HTML)),
        COMMENT(Status.OK, COMMENT_PATH + COMMENT_HTML, List.of(COMMENT_PATH, COMMENT_PATH + COMMENT_HTML));

        private final Status status;

        private final String defaultHtml;

        private final List<String> pathList;

        AuthorizedEndPoint(Status status, String defaultHtml, List<String> pathList) {
            this.status = status;
            this.defaultHtml = defaultHtml;
            this.pathList = pathList;
        }
    }

    public enum UnAuthorizedEndPoint {
        AUTHORIZED_MAIN(Status.OK, BASE_HTML, List.of(BASE_PATH, BASE_HTML)),
        UN_AUTHORIZED_MAIN(Status.REDIRECT, LOGIN_PATH + LOGIN_HTML,
                List.of(BASE_PATH, BASE_HTML, AUTHORIZED_BASE_PATH, AUTHORIZED_BASE_PATH + BASE_HTML)),
        REGISTRATION(Status.OK, REGISTRATION_PATH + REGISTRATION_HTML,
                List.of(REGISTRATION_PATH, REGISTRATION_PATH + REGISTRATION_HTML)),
        LOGIN(Status.OK, LOGIN_PATH + LOGIN_HTML, List.of(LOGIN_PATH, LOGIN_PATH + LOGIN_HTML)),
        LOGIN_FAILED(Status.OK, LOGIN_PATH + LOGIN_FAIL_HTML,
                List.of(LOGIN_FAILED_PATH, LOGIN_FAILED_PATH + LOGIN_FAIL_HTML)),
        ARTICLE(Status.REDIRECT, LOGIN_PATH + LOGIN_HTML, List.of(ARTICLE_PATH, ARTICLE_PATH + ARTICLE_HTML)),
        COMMENT(Status.REDIRECT, LOGIN_PATH + LOGIN_HTML, List.of(COMMENT_PATH, COMMENT_PATH + COMMENT_HTML));
        private final Status status;

        private final String defaultHtml;

        private final List<String> pathList;

        UnAuthorizedEndPoint(Status status, String defaultHtml, List<String> pathList) {
            this.status = status;
            this.defaultHtml = defaultHtml;
            this.pathList = pathList;
        }
    }

    public enum Endpoint {
        BASE(BASE_HTML, List.of(BASE_PATH, BASE_HTML)),
        MAIN(AUTHORIZED_BASE_PATH + BASE_HTML,
                List.of(AUTHORIZED_BASE_PATH, AUTHORIZED_BASE_PATH + BASE_HTML)),
        REGISTRATION(REGISTRATION_PATH + REGISTRATION_HTML,
                List.of(REGISTRATION_PATH, REGISTRATION_PATH + REGISTRATION_HTML)),
        LOGIN(LOGIN_PATH + LOGIN_HTML, List.of(LOGIN_PATH, LOGIN_PATH + LOGIN_HTML)),
        LOGIN_FAILED(LOGIN_PATH + LOGIN_FAIL_HTML,
                List.of(LOGIN_FAILED_PATH, LOGIN_FAILED_PATH + LOGIN_FAIL_HTML)),
        ARTICLE(ARTICLE_PATH + ARTICLE_HTML, List.of(ARTICLE_PATH, ARTICLE_PATH + ARTICLE_HTML)),
        COMMENT(COMMENT_PATH + COMMENT_HTML, List.of(COMMENT_PATH, COMMENT_PATH + COMMENT_HTML));
        private final String defaultHtml;

        private final List<String> pathList;

        Endpoint(String defaultHtml, List<String> pathList) {
            this.defaultHtml = defaultHtml;
            this.pathList = pathList;
        }
    }

    public static String mathDirectory(String filePath) {
        String endPoint = getEndPoint(filePath);
        return BASE_DIRECTORY_PATH + endPoint;
    }

    private static String getEndPoint(String filePath) {
        for (Endpoint endPoint : Endpoint.values()) {
            if (endPoint.pathList.contains(filePath)) {
                return endPoint.defaultHtml;
            }
        }
        return filePath;
    }

    public static String matchUserEndPoint(String file) {
        for (AuthorizedEndPoint endPoint : AuthorizedEndPoint.values()) {
            for (String filePath : endPoint.pathList) {
                if ((BASE_DIRECTORY_PATH + filePath).equals(file)) {
                    return BASE_DIRECTORY_PATH + endPoint.defaultHtml;
                }
            }
        }
        return file;
    }

    public static String matchUnknownEndPoint(String file) {
        for (UnAuthorizedEndPoint endPoint : UnAuthorizedEndPoint.values()) {
            for (String filePath : endPoint.pathList) {
                if ((BASE_DIRECTORY_PATH + filePath).equals(file)) {
                    return BASE_DIRECTORY_PATH + endPoint.defaultHtml;
                }
            }
        }
        return file;
    }
}
