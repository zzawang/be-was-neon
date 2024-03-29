package http.handler;

import static utils.Constant.CONTENT_TYPE_DELIMITER;

import http.response.ContentType;
import http.response.Status;
import java.io.File;
import manager.RequestManager;
import manager.ResponseManager;
import manager.SessionManager;
import utils.DirectoryMatcher;
import utils.StaticFileReader;

public abstract class CommandHandler {
    protected RequestManager requestManager;
    protected ResponseManager responseManager;
    protected SessionManager sessionManager;

    public void setManagers(RequestManager requestManager, ResponseManager responseManager) {
        this.requestManager = requestManager;
        this.responseManager = responseManager;
        this.sessionManager = new SessionManager(requestManager);
    }

    public void handleGetRequest() {
        responseManager.setErrorResponse(Status.BAD_REQUEST);
    }

    public void handlePostRequest() {
        responseManager.setErrorResponse(Status.BAD_REQUEST);
    }

    public void handlePutRequest() {
        responseManager.setErrorResponse(Status.BAD_REQUEST);
    }

    public void handleDeleteRequest() {
        responseManager.setErrorResponse(Status.BAD_REQUEST);
    }

    public void handleHeadRequest() {
        responseManager.setErrorResponse(Status.BAD_REQUEST);
    }

    public void handleConnectRequest() {
        responseManager.setErrorResponse(Status.BAD_REQUEST);
    }

    public void handleTraceRequest() {
        responseManager.setErrorResponse(Status.BAD_REQUEST);
    }

    public void handlePatchRequest() {
        responseManager.setErrorResponse(Status.BAD_REQUEST);
    }

    protected void serveHtmlFileFromDirectory(String path) {
        String filePathUrl = DirectoryMatcher.matchDirectory(path);
        StaticFileReader staticFileReader = generateStaticFileReader(path);
        byte[] responseBody = staticFileReader.readAllBytes();
        ContentType contentType = getContentType(filePathUrl);
        responseManager.setOkResponse(contentType, responseBody);
    }

    protected StaticFileReader generateStaticFileReader(String path) {
        String filePathUrl = DirectoryMatcher.matchDirectory(path);
        File file = new File(filePathUrl);
        return new StaticFileReader(file);
    }

    protected ContentType getContentType(String filePathUrl) {
        String[] parts = filePathUrl.split(CONTENT_TYPE_DELIMITER);
        String type = parts[parts.length - 1].toLowerCase();

        for (ContentType contentType : ContentType.values()) {
            if (contentType.name().equals(type)) {
                return contentType;
            }
        }
        throw new IllegalArgumentException();
    }
}