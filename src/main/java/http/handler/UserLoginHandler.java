package http.handler;

import static utils.Constant.AUTHORIZED_BASE_PATH;
import static utils.Constant.BASE_PATH;
import static utils.Constant.LOGIN_FAILED_PATH;
import static utils.Constant.LOGIN_PATH;

import db.UserDatabase;
import model.User;
import session.SessionManager;
import session.SidGenerator;
import utils.UserGenerator;

public class UserLoginHandler extends CommandHandler {
    private static final String COOKIE_SETTING_FORMAT = "sid=%s; Path=%s; Max-Age=3600";

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
        User user = userGenerator.createUser();
        User matchedUser = UserDatabase.findUserById(user.getId());

        if (user.matchUser(matchedUser)) {
            String sid = SidGenerator.generate();
            SessionManager.setSession(sid, matchedUser.getName());
            responseManager.setRedirectResponse(AUTHORIZED_BASE_PATH);
            responseManager.setCookie(String.format(COOKIE_SETTING_FORMAT, sid, BASE_PATH));
            return;
        }
        responseManager.setRedirectResponse(LOGIN_FAILED_PATH);
    }
}
