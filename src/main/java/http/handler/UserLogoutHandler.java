package http.handler;

import static utils.Constant.BASE_PATH;

import manager.SessionManager;

/**
 * 사용자 로그아웃 처리를 담당하는 클래스
 */
public class UserLogoutHandler extends CommandHandler {
    private static final String COOKIE_DELETE_FORMAT = "sid=%s; Path=%s; Max-Age=0";

    /**
     * 인증된 사용자가 아닌 경우 기본 경로로 리다이렉션한다.
     * 세션을 만료시키고 쿠키의 max-age를 0으로 설정하여 삭제함으로써 로그아웃한다.
     * 로그아웃 처리가 끝난 후 기본 경로로 리다이렉션한다.
     */
    @Override
    public void handlePostRequest() {
        if (!sessionManager.isAuthorizedUser()) {
            responseManager.setRedirectResponse(BASE_PATH);
            return;
        }

        SessionManager sessionManager = new SessionManager(requestManager, sessionDb);
        sessionManager.expireSession();
        String cookie = requestManager.getCookie().get();
        responseManager.setCookie(String.format(COOKIE_DELETE_FORMAT, cookie, BASE_PATH));
        responseManager.setRedirectResponse(BASE_PATH);
    }
}
