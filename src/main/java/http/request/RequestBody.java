package http.request;

import static utils.Constant.EMPTY;

import java.nio.charset.StandardCharsets;
import model.Article;
import utils.ArticleGenerator;
import utils.Decoder;

/**
 * HTTP request body를 나타내는 클래스
 */
public class RequestBody {
    private static final String REPLACE_USER = "userId|userPw|userName|userEmail|=";
    private static final String REPLACE_COMMENT = "commentContent|=";
    private static final String SPLIT_REGEX = "&";

    private final byte[] body;

    /**
     * RequestBody 클래스의 생성자
     *
     * @param body request body의 바이트 배열
     */
    public RequestBody(byte[] body) {
        this.body = body;
    }

    /**
     * body에서 사용자 정보를 추출한다.
     *
     * @return 사용자 정보 문자열
     */
    public String[] extractUser() {
        String convertedBody = new String(body, StandardCharsets.UTF_8);
        String replacedBody = convertedBody.replaceAll(REPLACE_USER, EMPTY);
        return replacedBody.split(SPLIT_REGEX); // id, pw, name, email만 추출
    }

    /**
     * body에서 게시물을 생성한다.
     *
     * @param userName 게시물 작성자 이름
     * @return 생성된 게시물 객체
     */
    public Article generateArticle(String userName) {
        return ArticleGenerator.generateArticle(body, userName);
    }

    /**
     * body에서 댓글을 추출한다.
     *
     * @return 댓글 문자열
     */
    public String extractComment() {
        String convertedBody = new String(body, StandardCharsets.UTF_8);
        String replacedBody = convertedBody.replaceAll(REPLACE_COMMENT, EMPTY).trim();
        return Decoder.decodeStr(replacedBody);
    }
}
