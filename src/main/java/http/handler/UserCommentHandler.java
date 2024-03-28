package http.handler;

import static utils.Constant.BASE_PATH;
import static utils.Constant.COMMENT_PATH;
import static utils.Constant.LOGIN_PATH;

public class UserCommentHandler extends CommandHandler {
    @Override
    public void handleGetRequest() {
        if (!sessionManager.isAuthorizedUser()) {
            responseManager.setRedirectResponse(LOGIN_PATH);
            return;
        }
        serveHtmlFileFromDirectory(COMMENT_PATH);
    }

    @Override
    public void handlePostRequest() {
        if (!sessionManager.isAuthorizedUser()) {
            responseManager.setRedirectResponse(LOGIN_PATH);
            return;
        }
        responseManager.setRedirectResponse(BASE_PATH);
    }
}
