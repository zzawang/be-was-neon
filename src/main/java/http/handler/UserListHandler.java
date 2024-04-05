package http.handler;

import static utils.Constant.LINE_FEED;
import static utils.Constant.LIST_HTML;
import static utils.Constant.LOGIN_PATH;
import static utils.Constant.USER_PATH;

import http.response.ContentType;
import java.nio.charset.StandardCharsets;
import java.util.List;
import model.Session;
import utils.StaticFileReader;

/**
 * 사용자 목록 처리를 담당하는 클래스
 */
public class UserListHandler extends CommandHandler {
    private static final String LIST_REGEX = "user_table_replacement";
    private static final String LIST_REPLACE_STR = "<tr><th scope=\"row\">%d</th><td>%s</td><td>%s</td><td>%s</td></tr>";

    /**
     * 인증된 사용자가 아닌 경우 로그인 페이지로 리다이렉션한다.
     * 사용자 목록을 가져와 사용자 아이디, 이름, 이메일 정보를 담은 리스트로 만들어서 응답한다.
     */
    @Override
    public void handleGetRequest() {
        if (!sessionManager.isAuthorizedUser()) {
            responseManager.setRedirectResponse(LOGIN_PATH);
            return;
        }

        StaticFileReader staticFileReader = generateStaticFileReader(USER_PATH + LIST_HTML);
        byte[] responseBody = generateResponseBody(staticFileReader);
        responseManager.setOkResponse(ContentType.html, responseBody);
    }

    private byte[] generateResponseBody(StaticFileReader staticFileReader) {
        String content = new String(staticFileReader.readAllBytes(), StandardCharsets.UTF_8);
        String userInfo = generateUserInfo();
        content = content.replaceAll(LIST_REGEX, userInfo);
        return content.getBytes();
    }

    private String generateUserInfo() {
        StringBuilder sb = new StringBuilder();
        List<Session> users = sessionManager.getUsers();

        users.forEach(user -> {
            String userInfo = String.format(LIST_REPLACE_STR, user.getId(), user.getUserId(), user.getUserName(),
                    user.getUserEmail());
            sb.append(userInfo).append(LINE_FEED);
        });
        return sb.toString();
    }
}
