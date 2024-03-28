package http.handler;

import static utils.Constant.AUTHORIZED_BASE_PATH;
import static utils.Constant.BASE_PATH;

public class HomeHandler extends CommandHandler {
    @Override
    public void handleGetRequest() {
        if (sessionManager.isAuthorizedUser()) {
            responseManager.setRedirectResponse(AUTHORIZED_BASE_PATH);
            return;
        }
        serveHtmlFileFromDirectory(BASE_PATH);
    }
}
