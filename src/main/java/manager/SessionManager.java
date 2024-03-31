package manager;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import model.Session;
import model.User;
import repository.JdbcSessionRepository;

public class SessionManager {
    private final RequestManager requestManager;
    private final JdbcSessionRepository sessionDb;

    public SessionManager(RequestManager requestManager, JdbcSessionRepository sessionDb) {
        this.requestManager = requestManager;
        this.sessionDb = sessionDb;
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
        Optional<Session> session = sessionDb.findSessionBySid(sid);
        if (session.isEmpty()) {

        }

        return session.get().getUserName();
    }

    public List<Session> getUsers() {
        return sessionDb.findAllSession().stream().toList();
    }

    public void expireSession() {
        String sid = requestManager.getSid();
        sessionDb.removeSession(sid);
    }

    public String setSession(User user) {
        String sid = generate();
        Session session = new Session(sid, user.getUserId(), user.getUserName(), user.getUserEmail());
        sessionDb.addSession(session);
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
        return sessionDb.isSessionIdPresent(sid);
    }
}
