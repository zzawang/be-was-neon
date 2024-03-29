package db;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import model.Session;

public class SessionDatabase {
    private static Map<String, Session> sessions = new ConcurrentHashMap<>();

    public static void addSession(String sid, Session session) {
        sessions.put(sid, session);
    }

    public static boolean findSessionId(String sid) {
        return sessions.containsKey(sid);
    }

    public static Session findSession(String sid) {
        return sessions.get(sid);
    }

    public static Collection<Session> findAllUserName() {
        return sessions.values();
    }

    public static void removeSession(String sid) {
        sessions.remove(sid);
    }
}
