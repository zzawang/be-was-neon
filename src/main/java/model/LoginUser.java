package model;

import java.util.Optional;

/**
 * 로그인 사용자를 나타내는 클래스
 */
public class LoginUser {
    private String userId;
    private String userPw;

    /**
     * LoginUser 클래스의 생성자
     *
     * @param userId 사용자 ID
     * @param userPw 사용자 비밀번호
     */
    public LoginUser(String userId, String userPw) {
        this.userId = userId;
        this.userPw = userPw;
    }

    /**
     * 사용자 ID를 반환한다.
     *
     * @return 사용자 ID
     */
    public String getUserId() {
        return userId;
    }

    /**
     * 사용자 비밀번호를 반환한다.
     *
     * @return 사용자 비밀번호
     */
    public String getUserPw() {
        return userPw;
    }

    /**
     * 주어진 사용자와 일치하는지 확인한다.
     *
     * @param user 확인할 사용자
     * @return 사용자가 일치하면 true를 반환하고, 그렇지 않으면 false를 반환한다.
     */
    public boolean matchUser(Optional<User> user) {
        return user.filter(value -> this.userId.equals(value.getUserId()) && this.userPw.equals(value.getUserPw()))
                .isPresent();
    }
}
