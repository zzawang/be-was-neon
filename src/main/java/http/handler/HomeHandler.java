package http.handler;

import static utils.Constant.AUTHORIZED_BASE_PATH;
import static utils.Constant.BASE_PATH;

/**
 * 로그인하지 않은 사용자가 볼 수 있는 기본 페이지 요청에 대한 정적 파일을 제공하는 클래스
 * CommandHandler 클래스의 handleGetRequest 메서드를 오버라이드하여 파일을 제공하는 방법을 구현한다.
 */
public class HomeHandler extends CommandHandler {
    /**
     * 기본 페이지 html파일을 제공한다.
     * 만약 로그인한 사용자라면 로그아웃 버튼이 보이는 기본 페이지로 redirect한다.
     */
    @Override
    public void handleGetRequest() {
        if (sessionManager.isAuthorizedUser()) {
            responseManager.setRedirectResponse(AUTHORIZED_BASE_PATH);
            return;
        }
        serveFileFromDirectory(BASE_PATH);
    }
}
