package http.handler;

import static utils.Constant.AUTHORIZED_BASE_PATH;
import static utils.Constant.COMMENT_PATH;
import static utils.Constant.EMPTY;
import static utils.Constant.LOGIN_PATH;

import java.util.Optional;
import model.Comment;

public class UserCommentHandler extends CommandHandler {
    private static final String ARTICLE_REGEX = "\s*article=\s*";
    private static final String NAV_PATH = "/main?article=%d";

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
        Optional<String> queryOpt = requestManager.getQuery();
        if (queryOpt.isEmpty()) {
            responseManager.setRedirectResponse(AUTHORIZED_BASE_PATH);
            return;
        }
        
        String query = queryOpt.get().replaceAll(ARTICLE_REGEX, EMPTY);
        long aid = Long.parseLong(query);
        String content = requestManager.extractComment();
        commentDb.addComment(new Comment(aid, content));
        responseManager.setRedirectResponse(String.format(NAV_PATH, aid));
    }
}
