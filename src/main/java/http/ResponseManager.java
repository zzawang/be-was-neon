package http;

import static utils.Constant.CRLF;

import http.response.ContentType;
import http.response.Response;
import http.response.Status;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ResponseManager {
    private static final Logger logger = LoggerFactory.getLogger(ResponseManager.class);

    private final Response response;

    public ResponseManager() {
        this.response = new Response();
    }

    public void setOkResponse(ContentType contentType, byte[] body) {
        response.setOkResponse(contentType, body);
    }

    public void setRedirectResponse(String basicPath) {
        response.setRedirectResponse(basicPath);
    }

    public void setErrorResponse(Status status) {
        response.setErrorResponse(status);
    }

    public void setCookie(String cookie) {
        response.setCookie(cookie);
    }

    public void sendResponse(OutputStream out) throws IOException {
        DataOutputStream dos = new DataOutputStream(out);
        byte[] body = response.getResponseBody();
        String formattedBody = response.getResponseHeaders();
        try {
            dos.writeBytes(formattedBody);
            dos.writeBytes(CRLF);
            dos.write(body, 0, body.length);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
        logger.info(formattedBody);
        dos.flush();
    }
}
