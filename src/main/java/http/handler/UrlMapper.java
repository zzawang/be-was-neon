package http.handler;

import java.util.HashMap;
import java.util.Map;

public class UrlMapper {
    private static Map<String, CommandHandler> urlMap = new HashMap<>();

    static {
        urlMap.put("/", new HomeHandler());
        urlMap.put("/main", new UserHomeHandler());
        urlMap.put("/article", new UserArticleHandler());
        urlMap.put("/comment", new UserCommentHandler());
        urlMap.put("/registration", new UserCreateHandler());
        urlMap.put("/login", new UserLoginHandler());
        urlMap.put("/logout", new UserLogoutHandler());
        urlMap.put("/user/list", new UserListHandler());
    }

    public static boolean isValidCommand(String filePath) {
        return urlMap.containsKey(filePath);
    }

    public static CommandHandler matchCommandHandler(String filePath) {
        return urlMap.get(filePath);
    }
}
