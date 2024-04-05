package utils;

/**
 * 상수를 정의한 유틸리티 클래스입니다.
 */
public class Constant {
    private Constant() {
    }

    /** CRLF (Carriage Return Line Feed) */
    public static final String CRLF = "\r\n";

    /** 공백 문자열 정규식 패턴 */
    public static final String BLANK = "\\s+";

    /** 줄 바꿈 문자열 */
    public static final String LINE_FEED = "\n";

    /** 콘텐츠 타입 구분자 */
    public static final String CONTENT_TYPE_DELIMITER = "\\.";

    /** 빈 문자열 */
    public static final String EMPTY = "";

    /** 문자 인코딩 방식 */
    public static final String CHARSETS = "UTF-8";

    /** 오류 메시지 형식 */
    public static final String ERROR_MSG_FORMAT = "<h1>%s</h1>";

    /** 기본 디렉토리 경로 */
    public static final String BASE_DIRECTORY_PATH = "src/main/resources/static";

    /** 기본 경로 */
    public static final String BASE_PATH = "/";

    /** 기본 HTML 경로 */
    public static final String BASE_HTML = "/index.html";

    /** 인증된 기본 경로 */
    public static final String AUTHORIZED_BASE_PATH = "/main";

    /** 게시글 경로 */
    public static final String ARTICLE_PATH = "/article";

    /** 게시글 HTML 경로 */
    public static final String ARTICLE_HTML = "/article.html";

    /** 댓글 경로 */
    public static final String COMMENT_PATH = "/comment";

    /** 댓글 HTML 경로 */
    public static final String COMMENT_HTML = "/comment.html";

    /** 회원가입 경로 */
    public static final String REGISTRATION_PATH = "/registration";

    /** 회원가입 HTML 경로 */
    public static final String REGISTRATION_HTML = "/register.html";

    /** 로그인 경로 */
    public static final String LOGIN_PATH = "/login";

    /** 로그인 HTML 경로 */
    public static final String LOGIN_HTML = "/login.html";

    /** 로그인 실패 경로 */
    public static final String LOGIN_FAILED_PATH = "/login_failed";

    /** 로그인 실패 HTML 경로 */
    public static final String LOGIN_FAIL_HTML = "/login_failed.html";

    /** 사용자 경로 */
    public static final String USER_PATH = "/user";

    /** 목록 HTML 경로 */
    public static final String LIST_HTML = "/list.html";

    /** 이미지 경로 */
    public static final String IMG_PATH = "/img";

    /** 빈 게시글 경로 */
    public static final String EMPTY_ARTICLE_PATH = "/empty_article";

    /** 빈 게시글 HTML 경로 */
    public static final String EMPTY_ARTICLE_HTML = "/empty.html";

    /** 기본 이미지 파일 이름 */
    public static final String EMPTY_IMG = "basicimage.png";
}
