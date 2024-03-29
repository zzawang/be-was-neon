package http.handler;

import static utils.Constant.BASE_PATH;

import manager.SessionManager;

public class UserLogoutHandler extends CommandHandler {
    private static final String COOKIE_DELETE_FORMAT = "sid=%s; Path=%s; Max-Age=0";

    @Override
    public void handlePostRequest() {
        if (!sessionManager.isAuthorizedUser()) {
            responseManager.setRedirectResponse(BASE_PATH);
            return;
        }

        SessionManager sessionManager = new SessionManager(requestManager);
        sessionManager.expireSession();
        String cookie = requestManager.getCookie().get();
        responseManager.setCookie(String.format(COOKIE_DELETE_FORMAT, cookie, BASE_PATH));
        responseManager.setRedirectResponse(BASE_PATH);
    }
}
