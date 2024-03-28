package http.handler;

import static utils.Constant.LINE_FEED;
import static utils.Constant.LIST_HTML;
import static utils.Constant.LOGIN_PATH;
import static utils.Constant.USER_PATH;

import http.response.ContentType;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import model.User;
import utils.StaticFileReader;

public class UserListHandler extends CommandHandler {
    private static final String LIST_REGEX = "(<tbody class=\"user_list_table\">).*?(</tbody>)";
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
        String content = staticFileReader.readAsStr();
        Matcher listMatcher = Pattern.compile(LIST_REGEX).matcher(content);
        String replacement = "";
        if (listMatcher.find()) {
            String userInfo = generateUserInfo();
            replacement = listMatcher.replaceAll("$1" + userInfo + "$2");
        }
        return replacement.getBytes();
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
