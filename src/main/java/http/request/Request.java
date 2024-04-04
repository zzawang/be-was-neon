package http.request;

import static utils.Constant.LINE_FEED;

import http.Headers;
import http.Version;
import java.util.Optional;

/**
 * HTTP request를 나타내는 클래스
 */
public class Request {
    private static final String FIRST_LINE_MSG = "[First Line of HTTP Request] : ";
    private static final String FIRST_LINE = "%s %s %s\n";
    private static final String HEADERS_MSG = "[Headers of HTTP Request] : \n";
    private static final String HEADERS = "<%s> : %s\n";

    private Method method;
    private FilePath filePath;
    private Version version;
    private String query;
    private Headers requestHeaders;
    private RequestBody body;

    /**
     * HTTP request의 첫 줄을 설정한다.
     *
     * @param method  HTTP method
     * @param filePath HTTP path
     * @param version HTTP version
     */
    public void setFirstLine(Method method, FilePath filePath, Version version) {
        this.method = method;
        this.filePath = filePath;
        this.version = version;
    }

    /**
     * 쿼리를 설정한다.
     *
     * @param query 쿼리 문자열
     */
    public void setQuery(String query) {
        this.query = query;
    }

    /**
     * request 헤더를 설정한다.
     *
     * @param line request 헤더 문자열
     */
    public void setRequestHeaders(String line) {
        this.requestHeaders = new Headers();
        this.requestHeaders.setHeaders(line);
    }

    /**
     * request body를 설정한다.
     *
     * @param body request body 바이트 배열
     */
    public void setBody(byte[] body) {
        this.body = new RequestBody(body);
    }

    /**
     * request body를 반환한다.
     *
     * @return request body
     */
    public RequestBody getBody() {
        return body;
    }

    /**
     * HTTP method를 반환한다.
     *
     * @return HTTP method
     */
    public Method getMethod() {
        return method;
    }

    /**
     * HTTP path를 반환한다.
     *
     * @return HTTP path
     */
    public FilePath getFilePath() {
        return filePath;
    }

    /**
     * 쿼리를 Optional 형태로 반환한다.
     *
     * @return Optional 형태의 쿼리 문자열
     */
    public Optional<String> getQuery() {
        return Optional.ofNullable(query);
    }

    /**
     * 쿠키를 Optional 형태로 반환한다.
     *
     * @return Optional 형태의 쿠키 문자열
     */
    public Optional<String> getCookie() {
        return Optional.ofNullable(requestHeaders.findHeader("Cookie"));
    }

    /**
     * Content-Length 헤더를 Optional 형태로 반환한다.
     *
     * @return Optional 형태의 Content-Length 헤더 값
     */
    public Optional<String> getContentLength() {
        return Optional.ofNullable(requestHeaders.findHeader("Content-Length"));
    }

    /**
     * request body에서 사용자 정보를 추출한다.
     *
     * @return 사용자 정보 문자열 배열
     */
    public String[] extractUser() {
        return body.extractUser();
    }

    /**
     * request body에서 댓글 내용을 추출한다.
     *
     * @return 댓글 문자열
     */
    public String extractComment() {
        return body.extractComment();
    }

    /**
     * 로그에 요청을 출력하기 위해 request의 중요한 정보만을 담은 문자열을 생성한다.
     *
     * @return request에서 firstline + 중요한 헤더값만을 담은 문자열
     */
    public String getFormattedRequest() {
        StringBuilder sb = new StringBuilder();
        sb.append(FIRST_LINE_MSG).append(generateFirstLinePhrase()).append(LINE_FEED);
        sb.append(HEADERS_MSG);
        sb.append(String.format(HEADERS, "Host", requestHeaders.findHeader("Host")));
        sb.append(String.format(HEADERS, "Connection", requestHeaders.findHeader("Connection")));
        sb.append(String.format(HEADERS, "Cache-Control", requestHeaders.findHeader("Cache-Control")));
        sb.append(String.format(HEADERS, "Accept-Language", requestHeaders.findHeader("Accept-Language")));
        sb.append(String.format(HEADERS, "Content-Type", requestHeaders.findHeader("Content-Type")));
        sb.append(String.format(HEADERS, "Content-Length", requestHeaders.findHeader("Content-Length")));
        sb.append(String.format(HEADERS, "Cookie", requestHeaders.findHeader("Cookie")));

        return sb.toString();
    }

    private String generateFirstLinePhrase() {
        return String.format(FIRST_LINE, method.getMethodCommand(), filePath.getFilePath(), version.getVersion());
    }

    /**
     * Content-Type 헤더 값을 반환한다.
     *
     * @return Content-Type 헤더 값
     */
    public String getContentType() {
        return requestHeaders.findHeader("Content-Type");
    }
}