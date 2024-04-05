package http.response;

import static utils.Constant.CRLF;
import static utils.Constant.ERROR_MSG_FORMAT;

import http.Headers;
import http.Version;
import http.Version.ProtocolVersion;

/**
 * HTTP response를 나타내는 클래스
 */
public class Response {
    private static final String FIRST_LINE = "%s %s %s";

    private Version version;
    private Status status;
    private final Headers responseHeaders;
    private byte[] body;

    /**
     * Response 클래스의 생성자
     * header 정보를 주입받는다.
     */
    public Response() {
        this.responseHeaders = new Headers();
    }

    /**
     * 200 OK response를 설정한다.
     *
     * @param contentType 콘텐츠 타입
     * @param body        response body
     */
    public void setOkResponse(ContentType contentType, byte[] body) {
        this.version = new Version(ProtocolVersion.V_11.getVersion());
        this.status = Status.OK;
        this.responseHeaders.setContentLength(body.length);
        this.responseHeaders.setContentType(contentType);
        this.body = body;
    }

    /**
     * 302 Found Redirect response를 설정한다.
     *
     * @param redirectPath 리다이렉트 경로
     */
    public void setRedirectResponse(String redirectPath) {
        this.version = new Version(ProtocolVersion.V_11.getVersion());
        this.status = Status.REDIRECT;
        this.responseHeaders.setLocation(redirectPath);
    }

    /**
     * 400, 404 등의 오류 response를 설정한다.
     * 오류 메시지가 화면에 보여지도록 response body에 오류 내용을 작성한다.
     *
     * @param status 상태 코드
     */
    public void setErrorResponse(Status status) {
        byte[] body = String.format(ERROR_MSG_FORMAT, status.getMsg()).getBytes();
        this.version = new Version(ProtocolVersion.V_11.getVersion());
        this.status = status;
        this.responseHeaders.setContentLength(body.length);
        this.responseHeaders.setContentType(ContentType.html);
        this.body = body;
    }

    /**
     * 쿠키를 설정한다.
     *
     * @param cookie 쿠키 문자열
     */
    public void setCookie(String cookie) {
        this.responseHeaders.setCookie(cookie);
    }

    /**
     * 응답 헤더를 반환한다.
     *
     * @return response header 문자열
     */
    public String getResponseHeaders() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format(FIRST_LINE, version.getVersion(), status.getCode(), status.getMsg())).append(CRLF);
        sb.append(responseHeaders.getHeaders()).append(CRLF);
        return sb.toString();
    }

    /**
     * response body를 반환한다.
     *
     * @return response body 바이트 배열
     */
    public byte[] getResponseBody() {
        return body;
    }
}