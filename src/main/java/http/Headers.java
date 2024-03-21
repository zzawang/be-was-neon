package http;

import static utils.Constant.CRLF;
import static utils.Constant.LINE_FEED;

import http.response.ContentType;
import java.util.LinkedHashMap;
import java.util.Map;

public class Headers {
    private static final String HEADER_DELIMITER = ":\s*";
    private static final String HEADER = "%s: %s";
    private static final int HEADER_DELIMITER_LIMIT_COUNT = 2;
    private static final int KEY_INDEX = 0;
    private static final int VALUE_INDEX = 1;

    private final Map<String, String> headers;

    public Headers() {
        this.headers = new LinkedHashMap<>();
    }

    public void setHeaders(String headers) {
        String[] header = headers.split(LINE_FEED);
        for (String line : header) {
            String[] keyNValue = line.split(HEADER_DELIMITER, HEADER_DELIMITER_LIMIT_COUNT);
            this.headers.put(keyNValue[KEY_INDEX], keyNValue[VALUE_INDEX]);
        }
    }

    public String findHeader(String key) {
        return this.headers.get(key);
    }

    public String getHeaders() {
        StringBuilder sb = new StringBuilder();
        headers.keySet().forEach(key -> sb.append(String.format(HEADER, key, headers.get(key))).append(CRLF));
        return sb.toString();
    }

    public void setContentLength(int length) {
        headers.put("Content-Length", String.valueOf(length));
    }

    public void setContentType(ContentType contentType) {
        headers.put("Content-Type", contentType.getContentType());
    }

    public void setLocation(String redirectPath) {
        headers.put("Location", redirectPath);
    }

    public void setCookie(String cookie) {
        headers.put("Set-Cookie", cookie);
    }
}
