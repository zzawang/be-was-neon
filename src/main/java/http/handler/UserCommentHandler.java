package http.handler;

import static utils.Constant.AUTHORIZED_BASE_PATH;
import static utils.Constant.COMMENT_PATH;
import static utils.Constant.EMPTY;
import static utils.Constant.LOGIN_PATH;

import java.util.Optional;
import model.Comment;

/**
 * 사용자 댓글 처리를 담당하는 클래스
 */
public class UserCommentHandler extends CommandHandler {
    private static final String ARTICLE_REGEX = "\s*article=\s*";
    private static final String NAV_PATH = "/main?article=%d";

    /**
     * 인증된 사용자인 경우 댓글 페이지를 제공한다. 그렇지 않은 경우 로그인 페이지로 리다이렉션한다.
     */
    @Override
    public void handleGetRequest() {
        if (!sessionManager.isAuthorizedUser()) {
            responseManager.setRedirectResponse(LOGIN_PATH);
            return;
        }
        serveFileFromDirectory(COMMENT_PATH);
    }

    /**
     * 인증되지 않은 사용자인 경우 로그인 페이지로 리다이렉션한다.
     * 쿼리가 없을 경우 잘못된 url이므로 인증된 사용자의 기본 경로로 리다이렉션한다.
     * 인증된 사용자이고 쿼리가 있을 경우 댓글을 추가하고 댓글을 추가한 게시글로 리다이렉션한다.
     */
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
