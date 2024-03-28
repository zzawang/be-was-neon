package http.handler;

import static utils.Constant.ARTICLE_PATH;
import static utils.Constant.AUTHORIZED_BASE_PATH;
import static utils.Constant.LOGIN_PATH;

public class UserArticleHandler extends CommandHandler {
    @Override
    public void handleGetRequest() {
        if (!sessionManager.isAuthorizedUser()) {
            responseManager.setRedirectResponse(LOGIN_PATH);
            return;
        }
        serveHtmlFileFromDirectory(ARTICLE_PATH);
    }

    @Override
    public void handlePostRequest() {
        responseManager.setRedirectResponse(AUTHORIZED_BASE_PATH);
    }
}
