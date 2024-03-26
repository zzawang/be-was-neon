package webserver;

import static utils.Constant.CHARSETS;

import http.RequestManager;
import http.RequestRouter;
import http.ResponseManager;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
    private static final String NEW_CLIENT_CONNECT_MESSAGE = "New Client Connect! Connected IP : {}, Port : {}";

    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug(NEW_CLIENT_CONNECT_MESSAGE, connection.getInetAddress(), connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            InputStreamReader inputStreamReader = new InputStreamReader(in, CHARSETS);
            BufferedReader br = new BufferedReader(inputStreamReader);

            RequestManager requestManager = new RequestManager(br);
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
