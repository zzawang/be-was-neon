package model;

public class User {
    private static final String USER_TO_STRING_FORMAT = "User [userId=%s, password=%s, name=%s, email=%s]";

    private long id;
    private String userId;
    private String userPw;
    private String userName;
    private String userEmail;

    public User(String userId, String userPw, String userName, String userEmail) {
        this.userId = userId;
        this.userPw = userPw;
        this.userName = userName;
        this.userEmail = userEmail;
    }

    public String getUserId() {
        return userId;
    }

    public String getUserPw() {
        return userPw;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    @Override
    public String toString() {
        return String.format(USER_TO_STRING_FORMAT, userId, userPw, userName, userEmail);
    }

    public void setId(long id) {
        this.id = id;
    }
}
