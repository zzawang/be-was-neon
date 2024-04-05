package http.handler;

import java.util.HashMap;
import java.util.Map;

/**
 * URL과 해당하는 CommandHandler를 매핑하는 클래스
 */
public class UrlMapper {
    private static Map<String, CommandHandler> urlMap = new HashMap<>();

    static { // 매핑 정보를 초기화
        urlMap.put("/", new HomeHandler());
        urlMap.put("/main", new UserHomeHandler());
        urlMap.put("/article", new UserArticleHandler());
        urlMap.put("/comment", new UserCommentHandler());
        urlMap.put("/registration", new UserCreateHandler());
        urlMap.put("/login", new UserLoginHandler());
        urlMap.put("/logout", new UserLogoutHandler());
        urlMap.put("/user/list", new UserListHandler());
    }

    /**
     * 주어진 url이 유효한 handler 요청인지 확인한다.
     *
     * @param filePath 요청하고자 하는 url.
     * @return url이 유효한 명령인 경우 true, 그렇지 않으면 false를 반환합니다.
     */
    public static boolean isValidCommand(String filePath) {
        return urlMap.containsKey(filePath);
    }

    /**
     * 지정된 파일 경로에 해당하는 CommandHandler를 반환한다.
     *
     * @param filePath CommandHandler와 매칭되는 url
     * @return 해당 파일 경로에 매핑된 CommandHandler. 파일 경로에 매핑된 CommandHandler가 없는 경우 null을 반환한다.
     */
    public static CommandHandler matchCommandHandler(String filePath) {
        return urlMap.get(filePath);
    }
}
