package http.handler;

import static utils.Constant.LINE_FEED;

import http.request.FilePath;
import http.response.ContentType;
import http.response.Status;
import java.io.File;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import model.User;
import session.SessionManager;
import utils.StaticFileReader;

public class FileHandler extends CommandHandler {
    private static final String ACCOUNT_REGEX = "(<p class=\"post__account__nickname\">).*?(</p>)";
    private static final String LIST_REGEX = "(<tbody class=\"user_list_table\">).*?(</tbody>)";
    private static final String LIST_REPLACE_STR = "<tr><th scope=\"row\">%d</th><td>%s</td><td>%s</td><td>%s</td></tr>";

    @Override
    public void handleGetRequest() {
        FilePath filePath = requestManager.getFilePath();
        File file = filePath.makeFile();
        StaticFileReader staticFileReader = new StaticFileReader(file);
        ContentType contentType = getContentType(filePath);
        byte[] responseBody = verifyResponseBody(staticFileReader);

        responseManager.setOkResponse(contentType, responseBody);
    }

    private byte[] verifyResponseBody(StaticFileReader staticFileReader) {
        SessionManager sessionManager = new SessionManager(requestManager);
        String content = staticFileReader.readAsStr();

        Matcher accountMatcher = Pattern.compile(ACCOUNT_REGEX).matcher(content);
        Matcher listMatcher = Pattern.compile(LIST_REGEX).matcher(content);

        if (sessionManager.isAuthorizedUser()) {
            String replacement = "";
            if (accountMatcher.find()) {
                String userName = sessionManager.getUserName();
                replacement = accountMatcher.replaceAll("$1" + userName + "$2");
            } else if (listMatcher.find()) {
                String userInfo = generateUserInfo(sessionManager);
                replacement = listMatcher.replaceAll("$1" + userInfo + "$2");
            }
            if (!replacement.isEmpty()) {
                return replacement.getBytes();
            }
        }

        return staticFileReader.readAllBytes();
    }

    private String generateUserInfo(SessionManager sessionManager) {
        StringBuilder sb = new StringBuilder();
        List<User> users = sessionManager.getUsers();
        for (int index = 0; index < users.size(); index++) {
            User user = users.get(index);
            sb.append(String.format(LIST_REPLACE_STR, index + 1, user.getId(), user.getName(), user.getEmail()))
                    .append(LINE_FEED);
        }
        return sb.toString();
    }

    @Override
    public void handlePostRequest() {
        responseManager.setErrorResponse(Status.BAD_REQUEST);
    }

    @Override
    public void handlePutRequest() {
        responseManager.setErrorResponse(Status.BAD_REQUEST);
    }

    @Override
    public void handleDeleteRequest() {
        responseManager.setErrorResponse(Status.BAD_REQUEST);
    }

    @Override
    public void handleHeadRequest() {
        responseManager.setErrorResponse(Status.BAD_REQUEST);
    }

    @Override
    public void handleConnectRequest() {
        responseManager.setErrorResponse(Status.BAD_REQUEST);
    }

    @Override
    public void handleTraceRequest() {
        responseManager.setErrorResponse(Status.BAD_REQUEST);
    }

    @Override
    public void handlePatchRequest() {
        responseManager.setErrorResponse(Status.BAD_REQUEST);
    }
}
