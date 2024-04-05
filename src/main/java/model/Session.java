package model;

/**
 * 세션을 나타내는 클래스
 */
public class Session {
    private long id;
    private String sid;
    private String userId;
    private String userName;
    private String userEmail;

    /**
     * ID를 포함한 Session 클래스의 생성자
     *
     * @param id       ID
     * @param sid      세션 ID
     * @param userId   사용자 ID
     * @param userName 사용자 이름
     * @param userEmail 사용자 이메일
     */
    public Session(long id, String sid, String userId, String userName, String userEmail) {
        this.id = id;
        this.sid = sid;
        this.userId = userId;
        this.userName = userName;
        this.userEmail = userEmail;
    }

    /**
     * ID를 포함하지 않은 Session 클래스의 생성자
     *
     * @param sid      세션 ID
     * @param userId   사용자 ID
     * @param userName 사용자 이름
     * @param userEmail 사용자 이메일
     */
    public Session(String sid, String userId, String userName, String userEmail) {
        this.sid = sid;
        this.userId = userId;
        this.userName = userName;
        this.userEmail = userEmail;
    }

    /**
     * ID를 반환한다.
     *
     * @return ID
     */
    public long getId() {
        return id;
    }

    /**
     * 세션 ID를 반환한다.
     *
     * @return 세션 ID
     */
    public String getSid() {
        return sid;
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
     * ID를 설정한다.
     *
     * @param id ID
     */
    public void setId(long id) {
        this.id = id;
    }
}
