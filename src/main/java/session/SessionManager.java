package session;

import http.RequestManager;
import java.util.Optional;

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

    public void expireSession() {
        String sid = requestManager.getSid();
        SessionMemory.removeSession(sid);
    }

    public static void setSession(String sid, String userId) {
        SessionMemory.addSession(sid, userId);
    }

    public static boolean isSessionIdExists(String sid) {
        return SessionMemory.findSessionId(sid);
    }
}
