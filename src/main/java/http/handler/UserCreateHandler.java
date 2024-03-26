package http.handler;

import static utils.Constant.BASE_PATH;

import db.UserDatabase;
import http.response.Status;
import model.User;
import utils.UserGenerator;

public class UserCreateHandler extends CommandHandler {
    @Override
    public void handleGetRequest() {
        responseManager.setErrorResponse(Status.BAD_REQUEST);
    }

    @Override
    public void handlePostRequest() {
        UserGenerator userGenerator = new UserGenerator(requestManager);
        User user = userGenerator.createUser();
        UserDatabase.addUser(user);
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
