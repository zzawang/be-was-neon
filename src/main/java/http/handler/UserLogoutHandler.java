package http.handler;

import static utils.Constant.BASE_PATH;

import http.response.Status;
import session.SessionManager;

public class UserLogoutHandler extends CommandHandler {
    @Override
    public void handleGetRequest() {
        responseManager.setErrorResponse(Status.BAD_REQUEST);
    }

    @Override
    public void handlePostRequest() {
        SessionManager sessionManager = new SessionManager(requestManager);
        sessionManager.expireSession();
        responseManager.setRedirectResponse(BASE_PATH);
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
