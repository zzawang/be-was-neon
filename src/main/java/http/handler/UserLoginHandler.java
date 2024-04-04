package http.handler;

import static utils.Constant.AUTHORIZED_BASE_PATH;
import static utils.Constant.BASE_PATH;
import static utils.Constant.LOGIN_FAILED_PATH;
import static utils.Constant.LOGIN_PATH;

import java.util.Optional;
import model.LoginUser;
import model.User;
import utils.UserGenerator;

/**
 * 사용자 로그인 처리를 담당하는 클래스
 */
public class UserLoginHandler extends CommandHandler {
    private static final String COOKIE_SETTING_FORMAT = "sid=%s; Path=%s";

    /**
     * 이미 인증된 사용자인 경우 인증된 사용자의 기본 경로로 리다이렉션한다.
     * 그렇지 않은 경우 로그인 페이지를 제공한다.
     */
    @Override
    public void handleGetRequest() {
        if (sessionManager.isAuthorizedUser()) {
            responseManager.setRedirectResponse(AUTHORIZED_BASE_PATH);
            return;
        }
        serveFileFromDirectory(LOGIN_PATH);
    }

    /**
     * 이미 인증된 사용자인 경우 인증된 사용자의 기본 경로로 리다이렉션한다.
     * 그렇지 않은 경우 회원가입된 유저 정보에 존재하는지 확인한다.
     * 성공할 경우 세션을 설정하고 인증된 사용자의 기본 경로로 리다이렉션한다.
     * 실패할 경우 로그인 실패 페이지로 리다이렉션한다.
     */
    @Override
    public void handlePostRequest() {
        if (sessionManager.isAuthorizedUser()) {
            responseManager.setRedirectResponse(AUTHORIZED_BASE_PATH);
            return;
        }

        UserGenerator userGenerator = new UserGenerator(requestManager);
        LoginUser loginUser = userGenerator.createLoginUser();
        Optional<User> expectedUser = userDb.findUserByUserId(loginUser.getUserId());

        if (loginUser.matchUser(expectedUser)) {
            String sid = sessionManager.setSession(expectedUser.get());
            responseManager.setRedirectResponse(AUTHORIZED_BASE_PATH);
            responseManager.setCookie(String.format(COOKIE_SETTING_FORMAT, sid, BASE_PATH));
            return;
        }
        responseManager.setRedirectResponse(LOGIN_FAILED_PATH);
    }
}
