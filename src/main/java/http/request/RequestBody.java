package http.request;

import static utils.Constant.EMPTY;

import java.nio.charset.StandardCharsets;

public class RequestBody {
    private static final String REPLACE_ID = "userId|userPw|userName|userEmail|=";
    private static final String SPLIT_REGEX = "&";

    private final byte[] body;

    public RequestBody(byte[] body) {
        this.body = body;
    }

    public String[] extractUser() {
        String convertedBody = new String(body, StandardCharsets.UTF_8);
        String replacedBody = convertedBody.replaceAll(REPLACE_ID, EMPTY);
        return replacedBody.split(SPLIT_REGEX); // id, pw, name, email만 추출
    }
}
