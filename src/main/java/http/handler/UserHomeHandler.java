package http.handler;

import static utils.Constant.AUTHORIZED_BASE_PATH;
import static utils.Constant.BASE_PATH;
import static utils.Constant.EMPTY;
import static utils.Constant.EMPTY_ARTICLE_PATH;
import static utils.Constant.EMPTY_IMG;
import static utils.Constant.IMG_PATH;

import http.response.ContentType;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;
import model.Article;
import model.Comment;
import utils.Decoder;
import utils.DirectoryMatcher;
import utils.StaticFileReader;

/**
 * 인증된 사용자의 기본 페이지 처리를 담당하는 클래스
 */
public class UserHomeHandler extends CommandHandler {
    private static final String ARTICLE_REGEX = "\s*article=\s*";
    private static final String USER_NAME_REPLACEMENT = "user_name_replacement";
    private static final String ARTICLE_IMG_REPLACEMENT = "article_img_replacement";
    private static final String ARTICLE_CONTENT_REPLACEMENT = "article_content_replacement";
    private static final String COMMENT_BTN_REPLACEMENT = "comment_btn_replacement";
    private static final String COMMENT_ID_REPLACEMENT = "comment_id_replacement";
    private static final String COMMENT_PATH = "/comment?article=%d";
    private static final String COMMENT_HTML = """
                        <li class=\"comment__item\">
                            <div class=\"comment__item__user\">
                                <img class=\"comment__item__user__img\" src="/img/post-account.png"/>
                                <p class=\"comment__item__user__nickname\">%s</p>
                            </div>
                            <p class=\"comment__item__article\">
                                %s
                            </p>
                        </li>
            """;
    private static final String PRE_ARTICLE_ID_REPLACEMENT = "pre_article_id_replacement";
    private static final String NEXT_ARTICLE_ID_REPLACEMENT = "next_article_id_replacement";
    private static final String NAV_PATH = "/main?article=%d";
    private static final String EMPTY_NAV = "<li class=\"empty__nav__menu__item\"></li>";
    private static final String PRE_NAV_HTML = """
                            <li class=\"nav__menu__item\">
                                <a class=\"nav__menu__item__btn\" href=\"pre_article_id_replacement\">
                                    <img
                                            class=\"nav__menu__item__img\"
                                            src=\"/img/ci_chevron-left.svg\"
                                    />
                                    이전 글
                                </a>
                            </li>
            """;
    private static final String NEXT_NAV_HTML = """
                            <li class=\"nav__menu__item\">
                                <a class=\"nav__menu__item__btn\" href=\"next_article_id_replacement\">
                                    다음 글
                                    <img
                                            class=\"nav__menu__item__img\"
                                            src=\"/img/ci_chevron-right.svg\"
                                    />
                                </a>
                            </li>
            """;

    /**
     * 인증된 사용자가 아닌 경우 기본 경로로 리다이렉션한다.
     * 아무 게시글도 없거나 잘못된 페이지 번호에 접근했을 경우 페이지 경고 경로로 리다이렉션한다.
     * 게시물을 로드하고 해당 게시물의 댓글을 생성하여 응답한다.
     */
    @Override
    public void handleGetRequest() {
        if (!sessionManager.isAuthorizedUser()) {
            responseManager.setRedirectResponse(BASE_PATH);
            return;
        }
        long aid = getArticleId();
        Optional<Article> article = articleDb.findArticleByAid(aid);
        if (article.isEmpty()) { // 아무 게시글도 없거나 잘못된 페이지 번호에 접근했을때
            responseManager.setRedirectResponse(EMPTY_ARTICLE_PATH);
            return;
        }

        StaticFileReader staticFileReader = generateStaticFileReader(AUTHORIZED_BASE_PATH);
        String articleBody = generateArticle(staticFileReader, article.get());
        String commentBody = generateComment(aid, articleBody);
        String responseBody = generateArticleNav(aid, commentBody);
        responseManager.setOkResponse(ContentType.html, responseBody.getBytes());
    }

    private String generateArticle(StaticFileReader staticFileReader, Article article) {
        String content = new String(staticFileReader.readAllBytes(), StandardCharsets.UTF_8);
        content = content.replaceAll(USER_NAME_REPLACEMENT, article.getUserName());
        String filePath = verifyFilePath(Decoder.decodeStr(article.getFilePath()));
        content = content.replaceAll(ARTICLE_IMG_REPLACEMENT, filePath);
        content = content.replaceAll(ARTICLE_CONTENT_REPLACEMENT, article.getContent());
        return content;
    }

    private String verifyFilePath(String decodeStr) {
        String matchDirectory = DirectoryMatcher.matchDirectory(decodeStr);
        File file = new File(matchDirectory);
        if (file.exists() && file.isFile()) {
            return decodeStr;
        }
        return IMG_PATH + BASE_PATH + EMPTY_IMG;
    }

    private String generateComment(long aid, String articleBody) {
        List<Comment> comments = commentDb.findAllCommentsByAid(aid).stream().toList();
        if (comments.isEmpty()) {
            return articleBody.replaceAll(COMMENT_ID_REPLACEMENT, EMPTY);
        }
        String userName = sessionManager.getUserName();
        StringBuilder sb = new StringBuilder();
        comments.stream().map(comment -> Decoder.decodeStr(comment.getContent()))
                .forEach(content -> sb.append(String.format(COMMENT_HTML, userName, content)));
        return articleBody.replaceAll(COMMENT_ID_REPLACEMENT, sb.toString());
    }

    private long getArticleId() {
        Optional<String> query = requestManager.getQuery();
        // 게시물 id를 명시하지 않은 url일 경우 자동으로 최신 게시물로 매핑
        return query.map(s -> Long.parseLong(s.replaceAll(ARTICLE_REGEX, EMPTY)))
                .orElseGet(() -> articleDb.getRecentAid());
    }

    private String generateArticleNav(long aid, String articleBody) {
        long preAid = aid - 1;
        long nextAid = aid + 1;

        if (isFirstArticle(aid)) {
            articleBody = articleBody.replaceAll(PRE_NAV_HTML, EMPTY_NAV);
        }
        if (isLastArticle(aid)) {
            articleBody = articleBody.replaceAll(NEXT_NAV_HTML, EMPTY_NAV);
        }
        articleBody = articleBody.replaceAll(PRE_ARTICLE_ID_REPLACEMENT, String.format(NAV_PATH, preAid));
        articleBody = articleBody.replaceAll(NEXT_ARTICLE_ID_REPLACEMENT, String.format(NAV_PATH, nextAid));
        articleBody = articleBody.replaceAll(COMMENT_BTN_REPLACEMENT, String.format(COMMENT_PATH, aid));
        return articleBody;
    }

    private boolean isFirstArticle(long aid) {
        return aid == 1;
    }

    private boolean isLastArticle(long aid) {
        return aid == articleDb.getRecentAid();
    }
}