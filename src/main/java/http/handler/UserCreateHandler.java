package http.handler;

import static utils.Constant.AUTHORIZED_BASE_PATH;
import static utils.Constant.BASE_PATH;
import static utils.Constant.REGISTRATION_PATH;

import model.User;
import utils.UserGenerator;

/**
 * 사용자 생성 처리를 담당하는 클래스
 */
public class UserCreateHandler extends CommandHandler {
    /**
     * 이미 인증된 사용자인 경우 인증된 사용자의 기본 경로로 리다이렉션한다.
     * 그렇지 않은 경우 회원가입 페이지를 제공한다.
     */
    @Override
    public void handleGetRequest() {
        if (sessionManager.isAuthorizedUser()) {
            responseManager.setRedirectResponse(AUTHORIZED_BASE_PATH);
            return;
        }
        serveFileFromDirectory(REGISTRATION_PATH);
    }

    /**
     * 사용자를 생성하고 데이터베이스에 추가한다.
     * 기본 경로로 리다이렉션한다.
     */
    @Override
    public void handlePostRequest() {
        UserGenerator userGenerator = new UserGenerator(requestManager);
        User user = userGenerator.createUser();
        userDb.addUser(user);
        responseManager.setRedirectResponse(BASE_PATH);
    }
}
