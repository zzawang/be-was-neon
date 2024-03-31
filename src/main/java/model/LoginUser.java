package model;

import java.util.Optional;

public class LoginUser {
    private String userId;
    private String userPw;

    public LoginUser(String userId, String userPw) {
        this.userId = userId;
        this.userPw = userPw;
    }

    public String getUserId() {
        return userId;
    }

    public String getUserPw() {
        return userPw;
    }

    public boolean matchUser(Optional<User> user) {
        return user.filter(value -> this.userId.equals(value.getUserId()) && this.userPw.equals(value.getUserPw()))
                .isPresent();
    }
}
