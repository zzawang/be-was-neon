package manager;

import db.SessionDatabase;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import model.Session;
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
        return SessionDatabase.findSession(sid).userName();
    }

    public List<Session> getUsers() {
        return SessionDatabase.findAllUserName().stream().toList();
    }

    public void expireSession() {
        String sid = requestManager.getSid();
        SessionDatabase.removeSession(sid);
    }

    public String setSession(User user) {
        String sid = generate();
        Session session = new Session(sid, user.getId(), user.getName(), user.getEmail());
        SessionDatabase.addSession(sid, session);
        return sid;
    }

    private String generate() {
        String sid = generateRandomSid();
        while (isSessionIdExists(sid)) {
            sid = generateRandomSid();
        }
        return sid;
    }

    private String generateRandomSid() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }

    private boolean isSessionIdExists(String sid) {
        return SessionDatabase.findSessionId(sid);
    }
}
