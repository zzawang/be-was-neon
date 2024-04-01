package manager;

import static utils.Constant.BLANK;
import static utils.Constant.CRLF;
import static utils.Constant.EMPTY;
import static utils.Constant.LINE_FEED;

import http.Version;
import http.request.FilePath;
import http.request.Method;
import http.request.Request;
import http.request.RequestBody;
import http.response.Status;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RequestManager {
    private static final Logger logger = LoggerFactory.getLogger(RequestManager.class);
    private static final String SID_EXTRACT_DELIMITER = "sid=";
    private static final String QUERY_STR = "?";
    private static final String QUERY_REGEX = "\\?";
    private static final int METHOD_INDEX = 0;
    private static final int FILE_PATH_INDEX = 1;
    private static final int VERSION_INDEX = 2;
    private final BufferedInputStream bis;
    private final Request request;
    private Status status; // request 상태 판별용

    public RequestManager(BufferedInputStream bis) throws IOException {
        this.bis = bis;
        this.request = new Request();
        this.status = Status.OK;
    }

    public void setRequest() throws IOException {
        try {
            setFirstLine();
            setRequestHeaders();
            setRequestBody();
            logger.debug(request.getFormattedRequest());
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
        FilePath filePath = extractQuery(lines[FILE_PATH_INDEX]);
        Version version = new Version(lines[VERSION_INDEX]);
        request.setFirstLine(method, filePath, version);
    }

    private FilePath extractQuery(String line) throws FileNotFoundException {
        if (line.contains(QUERY_STR)) {
            String[] lines = line.split(QUERY_REGEX, 2);
            request.setQuery(lines[1]);
            return new FilePath(lines[0]);
        }
        return new FilePath(line);
    }

    private String getFirstLine() throws IOException {
        String request = readLine();
        logger.debug(request);
        if (request.equals(EMPTY)) {
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
        while (!(request = readLine()).equals(EMPTY)) {
            headers.append(request).append(LINE_FEED);
        }
        return headers.toString();
    }

    private void setRequestBody() throws IOException, NumberFormatException {
        Optional<String> contentLengthHeader = request.getContentLength();
        if (contentLengthHeader.isPresent()) {
            int contentLength = Integer.parseInt(request.getContentLength().get().trim());
            byte[] body = getRequestBodyToBytes(contentLength);
            request.setBody(body);
        }
    }

    private byte[] getRequestBodyToBytes(int contentLength) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int bytesRead;
        int totalBytesRead = 0;
        while (totalBytesRead < contentLength
                && (bytesRead = bis.read(buffer, 0, Math.min(buffer.length, contentLength - totalBytesRead))) != -1) {
            baos.write(buffer, 0, bytesRead);
            totalBytesRead += bytesRead;
        }
        return baos.toByteArray();
    }

    private String readLine() throws IOException {
        StringBuilder sb = new StringBuilder();
        int bytes;
        while ((bytes = bis.read()) != -1) {
            char data = (char) bytes;
            sb.append(data);
            if (data == '\n') {
                break;
            }
        }
        return sb.toString().replaceAll(CRLF, EMPTY);
    }

    public Method getMethod() {
        return request.getMethod();
    }

    public FilePath getFilePath() {
        return request.getFilePath();
    }

    public String getContentType() {
        return request.getContentType();
    }

    public Optional<String> getQuery() {
        return request.getQuery();
    }


    public Optional<String> getCookie() {
        return request.getCookie();
    }

    public RequestBody getRequestBody() {
        return request.getBody();
    }

    public String[] extractUser() {
        return request.extractUser();
    }

    public Status getStatus() {
        return status;
    }

    public boolean isOk() {
        return status.equals(Status.OK);
    }

    public String getSid() {
        String cookie = getCookie().get();
        return cookie.replaceAll(SID_EXTRACT_DELIMITER, EMPTY);
    }
}
