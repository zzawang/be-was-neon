package http.request;

import static utils.Constant.EMPTY;

import java.nio.charset.StandardCharsets;
import model.Article;
import utils.ArticleGenerator;
import utils.Decoder;

public class RequestBody {
    private static final String REPLACE_USER = "userId|userPw|userName|userEmail|=";
    private static final String REPLACE_COMMENT = "commentContent|=";
    private static final String SPLIT_REGEX = "&";

    private final byte[] body;

    public RequestBody(byte[] body) {
        this.body = body;
    }

    public String[] extractUser() {
        String convertedBody = new String(body, StandardCharsets.UTF_8);
        String replacedBody = convertedBody.replaceAll(REPLACE_USER, EMPTY);
        return replacedBody.split(SPLIT_REGEX); // id, pw, name, email만 추출
    }

    public Article generateArticle(String userName) {
        return ArticleGenerator.generateArticle(body, userName);
    }

    public String extractComment() {
        String convertedBody = new String(body, StandardCharsets.UTF_8);
        String replacedBody = convertedBody.replaceAll(REPLACE_COMMENT, EMPTY).trim();
        return Decoder.decodeStr(replacedBody);
    }
}
