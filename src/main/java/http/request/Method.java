package http.request;

import http.handler.CommandHandler;
import java.util.Arrays;
import java.util.function.Consumer;

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

        public void execute(CommandHandler commandHandler) {
            function.accept(commandHandler);
        }
    }

    private static final String IS_INVALID_HTTP_METHOD = "올바른 HTTP Method가 아닙니다.";

    private final String method;

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

    public String getMethodCommand() {
        return method;
    }
}
