package http.request;

import static utils.Constant.EMPTY;

public class RequestBody {
    private static final String REPLACE_ID = "userId|userPw|userName|userEmail|=";
    private static final String SPLIT_REGEX = "&";

    private final String body;

    public RequestBody(String body) {
        this.body = body;
    }

    public String[] extractUser() {
        String bodyValue = body.replaceAll(REPLACE_ID, EMPTY);
        String[] result = new String[]{EMPTY, EMPTY, EMPTY, EMPTY};
        String[] values = bodyValue.split(SPLIT_REGEX); // id, pw, name, email만 추출

        // 분할된 값들을 배열에 넣기
        System.arraycopy(values, 0, result, 0, values.length);
        return result;
    }
}
