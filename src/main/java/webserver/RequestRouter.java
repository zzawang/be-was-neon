package webserver;

import static http.request.FilePath.IS_INVALID_FILE_PATH;

import http.handler.CommandHandler;
import http.handler.FileHandler;
import http.handler.UrlMapper;
import http.request.FilePath;
import http.request.Method;
import http.request.Method.HttpMethod;
import http.response.Status;
import java.io.FileNotFoundException;
import manager.RequestManager;
import manager.ResponseManager;
import utils.DirectoryMatcher;

public class RequestRouter {
    private final RequestManager requestManager;
    private final ResponseManager responseManager;

    public RequestRouter(RequestManager requestManager, ResponseManager responseManager) {
        this.requestManager = requestManager;
        this.responseManager = responseManager;
    }

    public void processRequest() {
        try {
            if (!requestManager.isOk()) {
                Status status = requestManager.getStatus();
                responseManager.setErrorResponse(status);
                return;
            }
            executeRequest();
        } catch (FileNotFoundException e) {
            responseManager.setErrorResponse(Status.NOT_FOUND);
        }
    }

    private void executeRequest() throws FileNotFoundException {
        CommandHandler commandHandler = getCommandHandler();
        commandHandler.setManagers(requestManager, responseManager);
        Method method = requestManager.getMethod();
        String methodCommand = method.getMethodCommand();
        HttpMethod.valueOf(methodCommand).execute(commandHandler);
    }

    private CommandHandler getCommandHandler() throws FileNotFoundException {
        FilePath filePath = requestManager.getFilePath();
        String filePathUrl = filePath.getFilePathUrl();
        String absoluteFilePathUrl = DirectoryMatcher.matchDirectory(filePathUrl);
        if (UrlMapper.isValidCommand(filePathUrl)) {
            return UrlMapper.matchCommandHandler(filePathUrl);
        }
        if (filePath.isValidFilePath(absoluteFilePathUrl)) {
            return new FileHandler();
        }
        throw new FileNotFoundException(IS_INVALID_FILE_PATH);
    }
}