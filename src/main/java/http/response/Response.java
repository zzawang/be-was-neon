package http.response;

import static utils.Constant.CRLF;
import static utils.Constant.ERROR_MSG_FORMAT;

import http.Headers;
import http.Version;
import http.Version.ProtocolVersion;

public class Response {
    private static final String FIRST_LINE = "%s %s %s";

    private Version version;
    private Status status;
    private final Headers responseHeaders;
    private byte[] body;

    public Response() {
        this.responseHeaders = new Headers();
    }

    public void setOkResponse(ContentType contentType, byte[] body) {
        this.version = new Version(ProtocolVersion.V_11.getVersion());
        this.status = Status.OK;
        this.responseHeaders.setContentLength(body.length);
        this.responseHeaders.setContentType(contentType);
        this.body = body;
    }

    public void setRedirectResponse(String redirectPath) {
        this.version = new Version(ProtocolVersion.V_11.getVersion());
        this.status = Status.REDIRECT;
        this.responseHeaders.setLocation(redirectPath);
    }

    public void setErrorResponse(Status status) {
        byte[] body = String.format(ERROR_MSG_FORMAT, status.getMsg()).getBytes();
        this.version = new Version(ProtocolVersion.V_11.getVersion());
        this.status = status;
        this.responseHeaders.setContentLength(body.length);
        this.responseHeaders.setContentType(ContentType.html);
        this.body = body;
    }

    public void setCookie(String cookie) {
        this.responseHeaders.setCookie(cookie);
    }

    public String getResponseHeaders() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format(FIRST_LINE, version.getVersion(), status.getCode(), status.getMsg())).append(CRLF);
        sb.append(responseHeaders.getHeaders()).append(CRLF);
        return sb.toString();
    }

    public byte[] getResponseBody() {
        return body;
    }
}