package http.handler;

import static utils.Constant.ARTICLE_PATH;
import static utils.Constant.AUTHORIZED_BASE_PATH;
import static utils.Constant.LOGIN_PATH;

import http.request.RequestBody;
import model.Article;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.RequestHandler;

public class UserArticleHandler extends CommandHandler {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
    private static final String FORM_DATA_REGEX = "^multipart/form-data;\s*boundary=----WebKitFormBoundary.+$";

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
        if (isFormData()) {
            String userName = sessionManager.getUserName();
            RequestBody requestBody = requestManager.getRequest().getBody();
            Article article = requestBody.generateArticle(userName);
            articleDb.addArticle(article);
            logger.info(article.toString());
        }
        responseManager.setRedirectResponse(AUTHORIZED_BASE_PATH);
    }

    private boolean isFormData() {
        String contentType = requestManager.getRequest().getContentType();
        return contentType.matches(FORM_DATA_REGEX);
    }
}
