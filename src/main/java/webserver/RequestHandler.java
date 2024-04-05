package webserver;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import manager.RequestManager;
import manager.ResponseManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 클라이언트의 요청을 처리하는 클래스
 */
public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
    private static final String NEW_CLIENT_CONNECT_MESSAGE = "New Client Connect! Connected IP : {}, Port : {}";

    private Socket connection;

    /**
     * RequestHandler 클래스의 생성자
     * @param connectionSocket 클라이언트와의 연결을 나타내는 소켓
     */
    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    /**
     * 클라이언트의 요청을 처리하는 메서드
     */
    public void run() {
        logger.debug(NEW_CLIENT_CONNECT_MESSAGE, connection.getInetAddress(), connection.getPort());

        try (BufferedInputStream bis = new BufferedInputStream(connection.getInputStream());
             OutputStream out = connection.getOutputStream()) {

            RequestManager requestManager = new RequestManager(bis);
            requestManager.setRequest();
            ResponseManager responseManager = new ResponseManager();

            RequestRouter requestRouter = new RequestRouter(requestManager, responseManager);
            requestRouter.processRequest();
            responseManager.sendResponse(out);
        } catch (IOException | IllegalArgumentException | NullPointerException e) {
            logger.error(e.getMessage());
        }
    }
}
