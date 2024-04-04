package http.handler;

import static utils.Constant.ARTICLE_PATH;
import static utils.Constant.AUTHORIZED_BASE_PATH;
import static utils.Constant.LOGIN_PATH;

import http.request.RequestBody;
import model.Article;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.RequestHandler;

/**
 * 사용자 게시글 처리를 담당하는 클래스
 */
public class UserArticleHandler extends CommandHandler {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
    private static final String FORM_DATA_REGEX = "^multipart/form-data;\s*boundary=----WebKitFormBoundary.+$";

    /**
     * 인증된 사용자인 경우 게시글 페이지를 보여준다. 그렇지 않은 경우 로그인 페이지로 리다이렉션한다.
     */
    @Override
    public void handleGetRequest() {
        if (!sessionManager.isAuthorizedUser()) {
            responseManager.setRedirectResponse(LOGIN_PATH);
            return;
        }
        serveFileFromDirectory(ARTICLE_PATH);
    }

    /**
     * 요청이 멀티파트 폼 데이터인 경우 게시글을 생성하고 데이터베이스에 추가한다.
     * 그렇지 않은 경우 인증된 사용자의 기본 경로로 리다이렉션한다.
     */
    @Override
    public void handlePostRequest() {
        if (isFormData()) {
            String userName = sessionManager.getUserName();
            RequestBody requestBody = requestManager.getRequestBody();
            Article article = requestBody.generateArticle(userName);
            articleDb.addArticle(article);
            logger.debug("게시글 {} 작성 완료", article);
        }
        responseManager.setRedirectResponse(AUTHORIZED_BASE_PATH);
    }

    /**
     * 요청이 멀티파트 폼 데이터인지 확인한다.
     *
     * @return 멀티파트 폼 데이터인 경우 true, 그렇지 않은 경우 false
     */
    private boolean isFormData() {
        String contentType = requestManager.getContentType();
        return contentType.matches(FORM_DATA_REGEX);
    }
}
