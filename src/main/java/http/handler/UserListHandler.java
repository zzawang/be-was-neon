package http.handler;

import static utils.Constant.LINE_FEED;
import static utils.Constant.LIST_HTML;
import static utils.Constant.LOGIN_PATH;
import static utils.Constant.USER_PATH;

import http.response.ContentType;
import java.nio.charset.StandardCharsets;
import java.util.List;
import model.User;
import utils.StaticFileReader;

public class UserListHandler extends CommandHandler {
    private static final String LIST_REGEX = "user_table_replacement";
    private static final String LIST_REPLACE_STR = "<tr><th scope=\"row\">%d</th><td>%s</td><td>%s</td><td>%s</td></tr>";

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
        List<User> users = sessionManager.getUsers();
        for (int index = 0; index < users.size(); index++) {
            User user = users.get(index);
            sb.append(String.format(LIST_REPLACE_STR, index + 1, user.getId(), user.getName(), user.getEmail()))
                    .append(LINE_FEED);
        }
        return sb.toString();
    }
}
