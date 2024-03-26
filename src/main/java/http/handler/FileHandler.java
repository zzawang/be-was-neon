package http.handler;

import http.request.FilePath;
import http.response.ContentType;
import http.response.Status;
import java.io.File;
import utils.FileReader;

public class FileHandler extends CommandHandler {
    @Override
    public void handleGetRequest() {
        FilePath filePath = requestManager.getFilePath();
        File file = filePath.makeFile();
        FileReader fileReader = new FileReader(file);
        ContentType contentType = getContentType(filePath);
        byte[] responseBody = fileReader.readAllBytes();

        responseManager.setOkResponse(contentType, responseBody);
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
