package http.request;

import static utils.Constant.LINE_FEED;

import http.Headers;
import http.Version;
import java.util.Optional;

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

    public void setFirstLine(Method method, FilePath filePath, Version version) {
        this.method = method;
        this.filePath = filePath;
        this.version = version;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public void setRequestHeaders(String line) {
        this.requestHeaders = new Headers();
        this.requestHeaders.setHeaders(line);
    }

    public void setBody(byte[] body) {
        this.body = new RequestBody(body);
    }

    public RequestBody getBody() {
        return body;
    }

    public Method getMethod() {
        return method;
    }

    public FilePath getFilePath() {
        return filePath;
    }

    public Optional<String> getQuery() {
        return Optional.ofNullable(query);
    }

    public Optional<String> getCookie() {
        return Optional.ofNullable(requestHeaders.findHeader("Cookie"));
    }

    public Optional<String> getContentLength() {
        return Optional.ofNullable(requestHeaders.findHeader("Content-Length"));
    }

    public String[] extractUser() {
        return body.extractUser();
    }

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
        return String.format(FIRST_LINE, method.getMethodCommand(), filePath.getFilePathUrl(), version.getVersion());
    }

    public String getContentType() {
        return requestHeaders.findHeader("Content-Type");
    }
}