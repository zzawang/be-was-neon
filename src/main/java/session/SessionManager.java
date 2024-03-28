package session;

import db.UserDatabase;
import http.RequestManager;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import model.User;

public class SessionManager {
    private final RequestManager requestManager;

    public SessionManager(RequestManager requestManager) {
        this.requestManager = requestManager;
    }

    public boolean isAuthorizedUser() {
        Optional<String> optCookie = requestManager.getCookie();
        if (optCookie.isEmpty()) {
            return false;
        }
        String sid = requestManager.getSid();
        if (isSessionIdExists(sid)) {
            return true;
        }
        return false;
    }

    public String getUserName() {
        String sid = requestManager.getSid();
        return SessionMemory.findUserNameBySid(sid);
    }

    public List<User> getUsers() {
        Collection<String> allUserName = SessionMemory.findAllUserName();
        List<User> users = new ArrayList<>();
        allUserName.forEach(userName -> users.add(UserDatabase.findUserByName(userName)));

        return users;
    }

    public void expireSession() {
        String sid = requestManager.getSid();
        SessionMemory.removeSession(sid);
    }

    public static void setSession(String sid, String userName) {
        SessionMemory.addSession(sid, userName);
    }

    public static boolean isSessionIdExists(String sid) {
        return SessionMemory.findSessionId(sid);
    }
}
