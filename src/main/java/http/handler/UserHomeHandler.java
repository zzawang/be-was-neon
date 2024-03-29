package http.handler;

import static utils.Constant.AUTHORIZED_BASE_PATH;
import static utils.Constant.BASE_PATH;
import static utils.Constant.EMPTY;
import static utils.Constant.EMPTY_ARTICLE_PATH;

import db.ArticleDatabase;
import http.response.ContentType;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import model.Article;
import utils.Decoder;
import utils.StaticFileReader;

public class UserHomeHandler extends CommandHandler {
    private static final String ARTICLE_REGEX = "\s*article=\s*";
    private static final String USER_NAME_REPLACEMENT = "user_name_replacement";
    private static final String ARTICLE_IMG_REPLACEMENT = "article_img_replacement";
    private static final String ARTICLE_CONTENT_REPLACEMENT = "article_content_replacement";
    private static final String PRE_ARTICLE_ID_REPLACEMENT = "pre_article_id_replacement";
    private static final String NEXT_ARTICLE_ID_REPLACEMENT = "next_article_id_replacement";
    private static final String NAV_PATH = "/main?article=%s";
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

    @Override
    public void handleGetRequest() {
        if (!sessionManager.isAuthorizedUser()) {
            responseManager.setRedirectResponse(BASE_PATH);
            return;
        }
        if (isArticleEmpty()) { // 아무 게시글도 없거나 잘못된 페이지 번호에 접근했을때
            responseManager.setRedirectResponse(EMPTY_ARTICLE_PATH);
            return;
        }

        StaticFileReader staticFileReader = generateStaticFileReader(AUTHORIZED_BASE_PATH);
        String articleBody = generateArticle(staticFileReader);
        String responseBody = generateArticleNav(articleBody);
        responseManager.setOkResponse(ContentType.html, responseBody.getBytes());
    }

    private boolean isArticleEmpty() {
        return ArticleDatabase.isEmpty() || !ArticleDatabase.hasArticleId(getArticleId());
    }

    private String generateArticle(StaticFileReader staticFileReader) {
        String aid = getArticleId();
        Article article = ArticleDatabase.findArticleBySequenceId(aid);
        String content = new String(staticFileReader.readAllBytes(), StandardCharsets.UTF_8);
        content = content.replaceAll(USER_NAME_REPLACEMENT, article.getUserName());
        content = content.replaceAll(ARTICLE_IMG_REPLACEMENT, Decoder.decodeStr(article.getFilePath()));
        content = content.replaceAll(ARTICLE_CONTENT_REPLACEMENT, article.getContent());
        return content;
    }

    private String getArticleId() {
        Optional<String> query = requestManager.getQuery();
        // 게시물 id를 명시하지 않은 url일 경우 자동으로 최신 게시물로 매핑
        return query.map(s -> s.replaceAll(ARTICLE_REGEX, EMPTY)).orElseGet(ArticleDatabase::getRecentSequenceId);
    }

    private String generateArticleNav(String articleBody) {
        String aid = getArticleId();
        int preAid = Integer.parseInt(aid) - 1;
        int nextAid = Integer.parseInt(aid) + 1;

        if (isFirstArticle(aid)) {
            articleBody = articleBody.replaceAll(PRE_NAV_HTML, EMPTY_NAV);
        }
        if (isLastArticle(aid)) {
            articleBody = articleBody.replaceAll(NEXT_NAV_HTML, EMPTY_NAV);
        }
        articleBody = articleBody.replaceAll(PRE_ARTICLE_ID_REPLACEMENT, String.format(NAV_PATH, preAid));
        articleBody = articleBody.replaceAll(NEXT_ARTICLE_ID_REPLACEMENT, String.format(NAV_PATH, nextAid));
        return articleBody;
    }

    private boolean isFirstArticle(String aid) {
        return aid.equals("1");
    }

    private boolean isLastArticle(String aid) {
        return aid.equals(ArticleDatabase.getRecentSequenceId());
    }
}