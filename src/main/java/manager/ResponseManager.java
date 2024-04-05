package manager;

import http.response.ContentType;
import http.response.Response;
import http.response.Status;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * HTTP response를 관리하는 클래스
 */
public class ResponseManager {
    private static final Logger logger = LoggerFactory.getLogger(ResponseManager.class);

    private final Response response;

    /**
     * ResponseManager 클래스의 생성자
     */
    public ResponseManager() {
        this.response = new Response();
    }

    /**
     * 성공 응답을 설정한다.
     *
     * @param contentType 콘텐츠 타입
     * @param body        response body
     */
    public void setOkResponse(ContentType contentType, byte[] body) {
        response.setOkResponse(contentType, body);
    }

    /**
     * 리다이렉트 응답을 설정한다.
     *
     * @param basicPath 기본 경로
     */
    public void setRedirectResponse(String basicPath) {
        response.setRedirectResponse(basicPath);
    }

    /**
     * 오류 응답을 설정한다.
     *
     * @param status 오류 상태
     */
    public void setErrorResponse(Status status) {
        response.setErrorResponse(status);
    }

    /**
     * 쿠키를 설정한다.
     *
     * @param cookie 쿠키
     */
    public void setCookie(String cookie) {
        response.setCookie(cookie);
    }

    /**
     * 응답을 전송한다.
     *
     * @param out 출력 스트림
     * @throws IOException 입출력 오류가 발생한 경우
     */
    public void sendResponse(OutputStream out) throws IOException {
        DataOutputStream dos = new DataOutputStream(out);
        String headers = response.getResponseHeaders();
        byte[] body = response.getResponseBody();
        try {
            dos.writeBytes(headers);
            dos.write(body, 0, body.length);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
        logger.debug(headers);
        dos.flush();
    }
}