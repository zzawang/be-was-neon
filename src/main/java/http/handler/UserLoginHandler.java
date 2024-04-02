package http.handler;

import static utils.Constant.AUTHORIZED_BASE_PATH;
import static utils.Constant.BASE_PATH;
import static utils.Constant.LOGIN_FAILED_PATH;
import static utils.Constant.LOGIN_PATH;

import java.util.Optional;
import model.LoginUser;
import model.User;
import utils.UserGenerator;

public class UserLoginHandler extends CommandHandler {
    private static final String COOKIE_SETTING_FORMAT = "sid=%s; Path=%s";

    @Override
    public void handleGetRequest() {
        if (sessionManager.isAuthorizedUser()) {
            responseManager.setRedirectResponse(AUTHORIZED_BASE_PATH);
            return;
        }
        serveHtmlFileFromDirectory(LOGIN_PATH);
    }

    @Override
    public void handlePostRequest() {
        if (sessionManager.isAuthorizedUser()) {
            responseManager.setRedirectResponse(AUTHORIZED_BASE_PATH);
            return;
        }

        UserGenerator userGenerator = new UserGenerator(requestManager);
        LoginUser loginUser = userGenerator.createLoginUser();
        Optional<User> expectedUser = userDb.findUserByUserId(loginUser.getUserId());

        if (loginUser.matchUser(expectedUser)) {
            String sid = sessionManager.setSession(expectedUser.get());
            responseManager.setRedirectResponse(AUTHORIZED_BASE_PATH);
            responseManager.setCookie(String.format(COOKIE_SETTING_FORMAT, sid, BASE_PATH));
            return;
        }
        responseManager.setRedirectResponse(LOGIN_FAILED_PATH);
    }
}
