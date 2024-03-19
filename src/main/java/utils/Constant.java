package utils;

public class Constant {
    public static final String CRLF = "\r\n";
    public static final String BLANK = "\s+";
    public static final String LINE_FEED = "\n";
    public static final String CONTENT_TYPE_DELIMITER = "\\.";
    public static final String EMPTY = "";
    public static final String CHARSETS = "UTF-8";
    public static final String ERROR_MSG_FORMAT = "<h1>%s</h1>";
    public static final String USER_CREATE_COMMAND = "/user/create";
    public static final String USER_LOGIN_COMMAND = "/user/login";
    public static final String BASE_PATH = "src/main/resources/static";
    public static final String BASIC_PATH = "/";
    public static final String BASIC_HTML = "/index.html";
    public static final String ARTICLE_PATH = "/article";
    public static final String ARTICLE_HTML = "/article.html";
    public static final String COMMENT_PATH = "/comment";
    public static final String COMMENT_HTML = "/comment.html";
    public static final String REGISTRATION_PATH = "/registration";
    public static final String REGISTRATION_HTML = "/register.html";
    public static final String LOGIN_PATH = "/login";
    public static final String LOGIN_HTML = "/login.html";
    public static final String LOGIN_FAILED_PATH = "/login_failed";
    public static final String LOGIN_FAIL_HTML = "/login_failed.html";
    public static final String COOKIE_SETTING_FORMAT = "sid=%s; Path=%s";
    public static final int SID_START_INDEX = 0;
    public static final int SID_MAX_LENGTH = 6;
    public static final int SID_RANGE_NUM = 10;
    public static final int ID_INDEX = 0;
    public static final int PW_INDEX = 1;
    public static final int NAME_INDEX = 2;
    public static final int EMAIL_INDEX = 3;
    public static final String IS_INVALID_HTTP_METHOD = "올바른 HTTP Method가 아닙니다.";
    public static final String IS_INVALID_FILE_PATH = "올바른 파일이 아닙니다.";
}
