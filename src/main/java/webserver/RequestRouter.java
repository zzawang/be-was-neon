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

/**
 * 클라이언트 요청을 적절한 핸들러로 라우팅하는 클래스
 */
public class RequestRouter {
    private final RequestManager requestManager;
    private final ResponseManager responseManager;

    /**
     * RequestRouter 클래스의 생성자
     * @param requestManager 클라이언트가 보낸 요청을 관리하는 RequestManager 객체
     * @param responseManager 서버로 전송할 응답을 관리하는 ResponseManager 객체
     */
    public RequestRouter(RequestManager requestManager, ResponseManager responseManager) {
        this.requestManager = requestManager;
        this.responseManager = responseManager;
    }

    /**
     * 클라이언트 요청을 처리한다.
     */
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

    /**
     * 클라이언트 요청을 실행한다.
     * @throws FileNotFoundException 파일이 발견되지 않았을 경우 발생하는 예외
     */
    private void executeRequest() throws FileNotFoundException {
        CommandHandler commandHandler = getCommandHandler();
        commandHandler.setManagers(requestManager, responseManager);
        Method method = requestManager.getMethod();
        String methodCommand = method.getMethodCommand();
        HttpMethod.valueOf(methodCommand).execute(commandHandler);
    }

    /**
     * 요청에 대한 적절한 핸들러를 가져온다.
     * @return CommandHandler 객체
     * @throws FileNotFoundException 파일이 발견되지 않았을 경우 발생하는 예외
     */
    private CommandHandler getCommandHandler() throws FileNotFoundException {
        FilePath filePath = requestManager.getFilePath();
        String filePathUrl = filePath.getFilePath();
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