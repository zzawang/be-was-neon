package http;

import static utils.Constant.CRLF;
import static utils.Constant.LINE_FEED;

import http.response.ContentType;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * HTTP request와 response의 헤더를 관리하는 클래스
 */
public class Headers {
    private static final String HEADER_DELIMITER = ":\s*";
    private static final String HEADER = "%s: %s";
    private static final int HEADER_DELIMITER_LIMIT_COUNT = 2;
    private static final int KEY_INDEX = 0;
    private static final int VALUE_INDEX = 1;

    private final Map<String, String> headers;

    /**
     * Headers 클래스의 생성자
     */
    public Headers() {
        this.headers = new LinkedHashMap<>();
    }

    /**
     * 헤더 문자열을 key-value형태인 map으로 저장한다.
     *
     * @param headers 헤더 문자열
     */
    public void setHeaders(String headers) {
        String[] header = headers.split(LINE_FEED);
        for (String line : header) {
            String[] keyNValue = line.split(HEADER_DELIMITER, HEADER_DELIMITER_LIMIT_COUNT);
            this.headers.put(keyNValue[KEY_INDEX], keyNValue[VALUE_INDEX].trim());
        }
    }

    /**
     * 지정된 키에 해당하는 헤더 값을 반환한다.
     *
     * @param key 헤더 키
     * @return 헤더 값
     */
    public String findHeader(String key) {
        return this.headers.get(key);
    }

    /**
     * 모든 헤더를 반환한다.
     *
     * @return 모든 헤더 문자열
     */
    public String getHeaders() {
        StringBuilder sb = new StringBuilder();
        headers.keySet().forEach(key -> sb.append(String.format(HEADER, key, headers.get(key))).append(CRLF));
        return sb.toString();
    }

    /**
     * Content-Length 헤더를 설정한다.
     *
     * @param length 콘텐츠 길이
     */
    public void setContentLength(int length) {
        headers.put("Content-Length", String.valueOf(length));
    }

    /**
     * Content-Type 헤더를 설정한다.
     *
     * @param contentType 콘텐츠 타입
     */
    public void setContentType(ContentType contentType) {
        headers.put("Content-Type", contentType.getContentType());
    }

    /**
     * Location 헤더를 설정한다.
     *
     * @param redirectPath 리다이렉트 경로
     */
    public void setLocation(String redirectPath) {
        headers.put("Location", redirectPath);
    }

    /**
     * Set-Cookie 헤더를 설정한다.
     *
     * @param cookie 쿠키 문자열
     */
    public void setCookie(String cookie) {
        headers.put("Set-Cookie", cookie);
    }
}
