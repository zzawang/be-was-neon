package model;

/**
 * 사용자를 나타내는 클래스
 */
public class User {
    private static final String USER_TO_STRING_FORMAT = "User [userId=%s, password=%s, name=%s, email=%s]";

    private long id;
    private String userId;
    private String userPw;
    private String userName;
    private String userEmail;

    /**
     * User 클래스의 생성자
     *
     * @param userId    사용자 ID
     * @param userPw    사용자 비밀번호
     * @param userName  사용자 이름
     * @param userEmail 사용자 이메일
     */
    public User(String userId, String userPw, String userName, String userEmail) {
        this.userId = userId;
        this.userPw = userPw;
        this.userName = userName;
        this.userEmail = userEmail;
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
     * 사용자 이름을 반환한다.
     *
     * @return 사용자 이름
     */
    public String getUserName() {
        return userName;
    }

    /**
     * 사용자 이메일을 반환한다.
     *
     * @return 사용자 이메일
     */
    public String getUserEmail() {
        return userEmail;
    }

    /**
     * 사용자의 ID를 설정한다.
     *
     * @param id 사용자 ID
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * 객체를 문자열로 표현한다.
     *
     * @return 알맞은 형식의 문자열로 표현한 User 객체
     */
    @Override
    public String toString() {
        return String.format(USER_TO_STRING_FORMAT, userId, userPw, userName, userEmail);
    }
}
