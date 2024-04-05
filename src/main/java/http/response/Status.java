package http.response;

/**
 * HTTP reponse Status code를 정의한 Enum
 */
public enum Status {
    OK("200", "OK"),
    CREATED("201", "Created"),
    NO_CONTENT("204", "No Content"),
    REDIRECT("302", "Found"),
    NOT_MODIFIED("304", "Not Modified"),
    BAD_REQUEST("400", "Bad Request"),
    UNAUTHORIZED("401", "Unauthorized"),
    FORBIDDEN("403", "Forbidden"),
    NOT_FOUND("404", "Not Found"),
    METHOD_NOT_ALLOWED("405", "Method Not Allowed"),
    INTERNAL_SERVER_ERROR("500", "Internal Server Error"),
    SERVICE_UNAVAILABLE("503", "Service Unavailable");

    private final String code;
    private final String msg;

    /**
     * Status 열거형의 생성자
     *
     * @param code 상태 코드
     * @param msg  상태 메시지
     */
    Status(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    /**
     * 상태 코드를 반환한다.
     *
     * @return 상태 코드 문자열
     */
    public String getCode() {
        return code;
    }

    /**
     * 상태 메시지를 반환한다.
     *
     * @return 상태 메시지 문자열
     */
    public String getMsg() {
        return msg;
    }
}
