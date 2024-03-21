package http.response;

import static utils.Constant.CRLF;
import static utils.Constant.ERROR_MSG_FORMAT;

import http.Version;
import http.Version.ProtocolVersion;

public class Response {
    private Version version;
    private Status status;
    private final ResponseHeaders responseHeaders;
    private byte[] body;

    public Response() {
        this.responseHeaders = new ResponseHeaders();
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

    public byte[] getResponseBody() {
        return body;
    }

    public String getResponseHeaders() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%s %s %s", version.getVersion(), status.getCode(), status.getMsg())).append(CRLF);
        if (status.equals(Status.REDIRECT)) {
            sb.append(String.format("Location: %s", responseHeaders.getLocation())).append(CRLF);
        } else {
            sb.append(String.format("Content-Type: %s", responseHeaders.getContentType())).append(CRLF);
            sb.append(String.format("Content-Length: %s", responseHeaders.getContentLength())).append(CRLF);
        }
        if (responseHeaders.isCookiePresent()) {
            sb.append(String.format("Set-Cookie: %s", responseHeaders.getCookie())).append(CRLF);
        }
        return sb.toString();
    }
}