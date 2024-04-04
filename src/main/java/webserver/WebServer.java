package webserver;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 웹 서버를 시작하는 클래스
 */
public class WebServer {
    private static final ExecutorService executorService = Executors.newCachedThreadPool();
    private static final Logger logger = LoggerFactory.getLogger(WebServer.class);
    private static final int DEFAULT_PORT = 8080;

    /**
     * 웹 서버를 실행하는 메인 메서드
     * @param args 명령행 인수
     * @throws Exception 예외가 발생할 경우
     */
    public static void main(String args[]) throws Exception {
        int port;
        if (args == null || args.length == 0) {
            port = DEFAULT_PORT;
        } else {
            port = Integer.parseInt(args[0]);
        }

        // 8080번 포트를 사용하는 서버 소켓 생성
        try (ServerSocket listenSocket = new ServerSocket(port)) {
            logger.info("Web Application Server started {} port.", port);

            Socket connection;
            while ((connection = listenSocket.accept()) != null) {
                RequestHandler requestHandler = new RequestHandler(connection);
                executorService.execute(requestHandler);
            }
        } finally {
            executorService.shutdown();
        }
    }
}
