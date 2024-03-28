package http.handler;

import static utils.Constant.AUTHORIZED_BASE_PATH;
import static utils.Constant.BASE_PATH;
import static utils.Constant.EMPTY;
import static utils.Constant.EMPTY_ARTICLE_PATH;

import db.ArticleDatabase;
import http.response.ContentType;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import model.Article;
import utils.Decoder;
import utils.StaticFileReader;

public class UserHomeHandler extends CommandHandler {
    private static final String ARTICLE_REGEX = "\s*article=\s*";
    private static final String ARTICLE_HTML_REGEX = """
                    (<div class=\"post\">
                        <div class=\"post__account\">
                            <img class=\"post__account__img\" src=\"/img/post-account.png\"/>
                            <p class=\"post__account__nickname\">).*?(</p>
                        </div>
                        <img class=\"post__img\" src=\").*?(\"/>
                        <div class=\"post__menu\">
                            <ul class=\"post__menu__personal\">
                                <li>
                                    <button class=\"post__menu__btn\">
                                        <img src=\"/img/like.svg\"/>
                                    </button>
                                </li>
                                <li>
                                    <button class=\"post__menu__btn\">
                                        <img src=\"/img/sendLink.svg\"/>
                                    </button>
                                </li>
                            </ul>
                            <button class=\"post__menu__btn\">
                                <img src=\"/img/bookMark.svg\"/>
                            </button>
                        </div>
                        <p class=\"post__article\">)
                            .*?
                        (</p>
                    </div>)
            """;
    private static final String NAV_PATH = "/main?article=%s";
    private static final String NAV_REPLACEMENT = "%s$4%s";
    private static final String EMPTY_NAV = "<li class=\"empty__nav__menu__item\"></li>";
    private static final String NAV_HTML_REGEX = """
                            (<li class=\"nav__menu__item\">
                                <a class=\"nav__menu__item__btn\" href=\").*?(\">)
                                    (<img
                                            class=\"nav__menu__item__img\"
                                            src=\"/img/ci_chevron-left.svg\"
                                    />
                                    이전 글
                                </a>
                            </li>)
                            (<li class=\"nav__menu__item\">
                                <a class=\"btn btn_ghost btn_size_m\" href=\"/comment\">댓글 작성</a>
                            </li>)
                            (<li class=\"nav__menu__item\">
                                <a class=\"nav__menu__item__btn\" href=\").*?(\">)
                                    (다음 글
                                    <img
                                            class=\"nav__menu__item__img\"
                                            src=\"/img/ci_chevron-right.svg\"
                                    />
                                </a>
                            </li>)
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
        String content = staticFileReader.readAsStr();
        String aid = getArticleId();
        Article article = ArticleDatabase.findArticleBySequenceId(aid);
        String filePath = Decoder.decodeStr(article.getFilePath());
        String articleReplacement = "$1" + article.getUserName() + "$2" + filePath + "$3" + article.getContent();
        Matcher accountMatcher = Pattern.compile(ARTICLE_HTML_REGEX).matcher(content);
        return accountMatcher.replaceAll(articleReplacement);
    }

    private String getArticleId() {
        Optional<String> query = requestManager.getQuery();
        // 게시물 id를 명시하지 않은 url일 경우 자동으로 최신 게시물로 매핑
        return query.map(s -> s.replaceAll(ARTICLE_REGEX, EMPTY)).orElseGet(ArticleDatabase::getRecentSequenceId);
    }

    private String generateArticleNav(String articleBody) {
        String navReplacement = generateNavReplacement();
        Matcher accountMatcher = Pattern.compile(NAV_HTML_REGEX).matcher(articleBody);
        return accountMatcher.replaceAll(navReplacement);
    }

    private String generateNavReplacement() {
        String aid = getArticleId();
        if (isFirstArticle(aid) && isLastArticle(aid)) {
            return String.format(NAV_REPLACEMENT, EMPTY_NAV, EMPTY_NAV);
        }

        int preAid = Integer.parseInt(aid) - 1;
        int nextAid = Integer.parseInt(aid) + 1;
        String preNavPath = "$1" + String.format(NAV_PATH, preAid) + "$2" + "$3";
        String nextNavPath = "$5" + String.format(NAV_PATH, nextAid) + "$6" + "$7";

        if (isFirstArticle(aid)) {
            return String.format(NAV_REPLACEMENT, EMPTY_NAV, nextNavPath);
        }
        if (isLastArticle(aid)) {
            return String.format(NAV_REPLACEMENT, preNavPath, EMPTY_NAV);
        }
        return String.format(NAV_REPLACEMENT, preNavPath, nextNavPath);
    }

    private boolean isFirstArticle(String aid) {
        return aid.equals("1");
    }

    private boolean isLastArticle(String aid) {
        return aid.equals(ArticleDatabase.getRecentSequenceId());
    }
}