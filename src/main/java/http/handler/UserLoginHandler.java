package http.handler;

import static utils.Constant.BASE_PATH;
import static utils.Constant.COOKIE_SETTING_FORMAT;
import static utils.Constant.LOGIN_FAILED_PATH;

import db.Database;
import http.response.Status;
import model.User;
import utils.SidGenerator;
import utils.UserGenerator;

public class UserLoginHandler extends CommandHandler {
    @Override
    public void handleGetRequest() {
        responseManager.setErrorResponse(Status.BAD_REQUEST);
    }

    @Override
    public void handlePostRequest() {
        UserGenerator userGenerator = new UserGenerator(requestManager);
        User user = userGenerator.createUser();
        User matchedUser = Database.findUserById(user.getId());

        if (user.matchUser(matchedUser)) {
            responseManager.setRedirectResponse(BASE_PATH);
            responseManager.setCookie(String.format(COOKIE_SETTING_FORMAT, SidGenerator.generate(), BASE_PATH));
            return;
        }
        responseManager.setRedirectResponse(LOGIN_FAILED_PATH);
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
