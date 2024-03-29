package model;

public class LoginUser {
    private String id;
    private String password;

    public LoginUser(String id, String password) {
        this.id = id;
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }

    public boolean matchUser(User user) {
        return user != null && this.id.equals(user.getId()) && this.password.equals(user.getPassword());
    }
}
