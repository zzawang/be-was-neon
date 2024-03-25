package http;

import static utils.Constant.BLANK;
import static utils.Constant.EMPTY;
import static utils.Constant.LINE_FEED;

import http.request.FilePath;
import http.request.Method;
import http.request.Request;
import http.response.Status;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.DirectoryMatcher;

public class RequestManager {
    private static final String SID_EXTRACT_DELIMITER = "sid=";
    private static final int METHOD_INDEX = 0;
    private static final int FILE_PATH_INDEX = 1;
    private static final int VERSION_INDEX = 2;
    private static final Logger logger = LoggerFactory.getLogger(RequestManager.class);
    private final BufferedReader br;
    private final Request request;
    private Status status; // request 상태 판별용

    public RequestManager(BufferedReader br) throws IOException {
        this.br = br;
        this.request = new Request();
        this.status = Status.OK;
    }

    public void setRequest() throws IOException {
        try {
            setFirstLine();
            setRequestHeaders();
            setRequestBody();
            logger.info(request.getFormattedRequest());
        } catch (FileNotFoundException e) {
            status = Status.NOT_FOUND;
        } catch (IllegalStateException e) {
            status = Status.BAD_REQUEST;
        }
    }

    private void setFirstLine() throws NullPointerException, IOException, IllegalStateException {
        String firstLine = getFirstLine();
        String[] lines = firstLine.split(BLANK);
        Method method = new Method(lines[METHOD_INDEX]);
        FilePath filePath = new FilePath(lines[FILE_PATH_INDEX]);
        Version version = new Version(lines[VERSION_INDEX]);
        request.setFirstLine(method, filePath, version);
    }

    private String getFirstLine() throws IOException {
        String request = br.readLine();
        logger.info(request);
        if (request == null) {
            throw new IOException();
        }
        return request;
    }

    private void setRequestHeaders() throws IOException {
        String headers = getRequestHeaders();
        request.setRequestHeaders(headers);
    }

    private String getRequestHeaders() throws IOException {
        StringBuilder headers = new StringBuilder();
        String request;
        while (!(request = br.readLine()).equals(EMPTY)) {
            headers.append(request).append(LINE_FEED);
        }
        return headers.toString();
    }

    private void setRequestBody() throws IOException, NumberFormatException {
        if (isBodyPresent()) {
            int contentLength = getContentLength();
            String body = getRequestBody(contentLength);
            request.setBody(body);
        }
    }

    private boolean isBodyPresent() {
        Optional<String> contentLengthHeader = request.getContentLength();
        if (contentLengthHeader.isEmpty()) {
            return false;
        }
        return true;
    }

    private int getContentLength() {
        int contentLength;
        String contentLengthHeader = request.getContentLength().get();
        contentLength = Integer.parseInt(contentLengthHeader.trim());
        return contentLength;
    }

    private String getRequestBody(int contentLength) throws IOException {
        StringBuilder body = new StringBuilder();
        char[] buffer = new char[1024];
        int bytesRead;
        int totalBytesRead = 0;
        while (totalBytesRead < contentLength
                && (bytesRead = br.read(buffer, 0, Math.min(buffer.length, contentLength - totalBytesRead))) != -1) {
            body.append(buffer, 0, bytesRead);
            totalBytesRead += bytesRead;
        }
        return body.toString();
    }

    public Method getMethod() {
        return request.getMethod();
    }

    public FilePath getFilePath() {
        return request.getFilePath();
    }

    public Status getStatus() {
        return status;
    }

    public Optional<String> getCookie() {
        return request.getCookie();
    }

    public String[] extractUser() {
        return request.extractUser();
    }

    public boolean isOk() {
        return status.equals(Status.OK);
    }

    public void changeUserFilePath() {
        String file = request.getFilePath().getFilePath();
        String validFile = DirectoryMatcher.matchUserEndPoint(file);
        request.setFilePath(validFile);
    }

    public String getSid() {
        String cookie = getCookie().get();
        return cookie.replaceAll(SID_EXTRACT_DELIMITER, EMPTY);
    }
}
