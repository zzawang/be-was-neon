package http.request;

import http.handler.CommandHandler;
import java.util.Arrays;
import java.util.function.Consumer;

/**
 * HTTP 메서드를 나타내는 클래스
 */
public class Method {
    public enum HttpMethod {
        GET(CommandHandler::handleGetRequest),
        POST(CommandHandler::handlePostRequest),
        PUT(CommandHandler::handlePutRequest),
        DELETE(CommandHandler::handleDeleteRequest),
        HEAD(CommandHandler::handleHeadRequest),
        CONNECT(CommandHandler::handleConnectRequest),
        TRACE(CommandHandler::handleTraceRequest),
        PATCH(CommandHandler::handlePatchRequest);

        private final Consumer<CommandHandler> function;

        HttpMethod(Consumer<CommandHandler> function) {
            this.function = function;
        }

        /**
         * HTTP 메서드에 대한 처리를 실행한다.
         *
         * @param commandHandler HTTP 요청에 대한 CommandHandler 자식 클래스
         */
        public void execute(CommandHandler commandHandler) {
            function.accept(commandHandler);
        }
    }

    private static final String IS_INVALID_HTTP_METHOD = "올바른 HTTP Method가 아닙니다.";

    private final String method;

    /**
     * Method 클래스의 생성자
     *
     * @param methodCommand HTTP 메서드
     */
    public Method(String methodCommand) {
        String method = methodCommand.toUpperCase();
        if (!isValidMethod(method)) {
            throw new IllegalStateException(IS_INVALID_HTTP_METHOD);
        }
        this.method = method;
    }

    private boolean isValidMethod(String method) {
        return Arrays.stream(HttpMethod.values())
                .anyMatch(httpMethod -> httpMethod.name().equals(method));
    }

    /**
     * HTTP 메서드를 반환한다.
     *
     * @return HTTP 메서드 문자열
     */
    public String getMethodCommand() {
        return method;
    }
}
