package http.handler;

import static utils.Constant.CONTENT_TYPE_DELIMITER;

import http.RequestManager;
import http.ResponseManager;
import http.request.FilePath;
import http.response.ContentType;

public abstract class CommandHandler {
    protected RequestManager requestManager;
    protected ResponseManager responseManager;

    public void setManagers(RequestManager requestManager, ResponseManager responseManager) {
        this.requestManager = requestManager;
        this.responseManager = responseManager;
    }

    public abstract void handleGetRequest();

    public abstract void handlePostRequest();

    public abstract void handlePutRequest();

    public abstract void handleDeleteRequest();

    public abstract void handleHeadRequest();

    public abstract void handleConnectRequest();

    public abstract void handleTraceRequest();

    public abstract void handlePatchRequest();

    protected ContentType getContentType(FilePath filePath) {
        String[] parts = filePath.getFilePathUrl().split(CONTENT_TYPE_DELIMITER);
        String type = parts[parts.length - 1];

        for (ContentType contentType : ContentType.values()) {
            if (contentType.name().equals(type)) {
                return contentType;
            }
        }
        throw new IllegalArgumentException();
    }
}