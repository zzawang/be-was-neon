package http.handler;

import http.request.FilePath;
import http.response.ContentType;
import http.response.Status;
import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import session.SessionManager;
import utils.StaticFileReader;

public class FileHandler extends CommandHandler {
    private static final String FIND_REGEX = "(<p class=\"post__account__nickname\">).*(</p>)";

    @Override
    public void handleGetRequest() {
        FilePath filePath = requestManager.getFilePath();
        File file = filePath.makeFile();
        StaticFileReader staticFileReader = new StaticFileReader(file);
        ContentType contentType = getContentType(filePath);
        byte[] responseBody = verifyResponseBody(staticFileReader);

        responseManager.setOkResponse(contentType, responseBody);
    }

    private byte[] verifyResponseBody(StaticFileReader staticFileReader) {
        SessionManager sessionManager = new SessionManager(requestManager);
        String content = staticFileReader.readAsStr();
        Pattern pattern = Pattern.compile(FIND_REGEX);
        Matcher matcher = pattern.matcher(content);

        if (sessionManager.isAuthorizedUser() && matcher.find()) {
            String userName = sessionManager.getUserName();
            content = matcher.replaceAll("$1" + userName + "$2");
            return content.getBytes();
        }
        return staticFileReader.readAllBytes();
    }

    @Override
    public void handlePostRequest() {
        responseManager.setErrorResponse(Status.BAD_REQUEST);
    }

    @Override
    public void handlePutRequest() {
        responseManager.setErrorResponse(Status.BAD_REQUEST);
    }

    @Override
    public void handleDeleteRequest() {
        responseManager.setErrorResponse(Status.BAD_REQUEST);
    }

    @Override
    public void handleHeadRequest() {
        responseManager.setErrorResponse(Status.BAD_REQUEST);
    }

    @Override
    public void handleConnectRequest() {
        responseManager.setErrorResponse(Status.BAD_REQUEST);
    }

    @Override
    public void handleTraceRequest() {
        responseManager.setErrorResponse(Status.BAD_REQUEST);
    }

    @Override
    public void handlePatchRequest() {
        responseManager.setErrorResponse(Status.BAD_REQUEST);
    }
}
