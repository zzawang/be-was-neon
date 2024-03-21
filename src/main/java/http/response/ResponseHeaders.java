package http.response;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

public class ResponseHeaders {
    private Integer contentLength;
    private ContentType contentType;
    private String location;
    private String cookie;
    private final Map<String, String> headers;

    public ResponseHeaders() {
        this.headers = new LinkedHashMap<>();
    }

    public Integer getContentLength() {
        return contentLength;
    }

    public String getContentType() {
        return contentType.getContentType();
    }

    public String getLocation() {
        return location;
    }

    public String getCookie() {
        return cookie;
    }

    public void setContentLength(Integer contentLength) {
        this.contentLength = contentLength;
    }

    public void setContentType(ContentType contentType) {
        this.contentType = contentType;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setCookie(String cookie) {
        this.cookie = cookie;
    }

    public boolean isCookiePresent() {
        Optional<String> cookie = Optional.ofNullable(this.cookie);

        return cookie.isPresent();
    }
}
