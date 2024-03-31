package model;

public class Session {
    private long id;
    private String sid;
    private String userId;
    private String userName;
    private String userEmail;

    public Session(String sid, String userId, String userName, String userEmail) {
        this.sid = sid;
        this.userId = userId;
        this.userName = userName;
        this.userEmail = userEmail;
    }

    public Session(long id, String sid, String userId, String userName, String userEmail) {
        this.id = id;
        this.sid = sid;
        this.userId = userId;
        this.userName = userName;
        this.userEmail = userEmail;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSid() {
        return sid;
    }

    public String getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserEmail() {
        return userEmail;
    }
}
