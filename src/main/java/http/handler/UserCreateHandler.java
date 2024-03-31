package http.handler;

import static utils.Constant.AUTHORIZED_BASE_PATH;
import static utils.Constant.BASE_PATH;
import static utils.Constant.REGISTRATION_PATH;

import model.User;
import utils.UserGenerator;

public class UserCreateHandler extends CommandHandler {
    @Override
    public void handleGetRequest() {
        if (sessionManager.isAuthorizedUser()) {
            responseManager.setRedirectResponse(AUTHORIZED_BASE_PATH);
            return;
        }
        serveHtmlFileFromDirectory(REGISTRATION_PATH);
    }

    @Override
    public void handlePostRequest() {
        UserGenerator userGenerator = new UserGenerator(requestManager);
        User user = userGenerator.createUser();
        userDb.addUser(user);
        responseManager.setRedirectResponse(BASE_PATH);
    }
}
