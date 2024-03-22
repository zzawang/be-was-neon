package session;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SessionMemory {
    private static Map<String, String> sessions = new ConcurrentHashMap<>();

    public static void addSession(String sid, String userId) {
        sessions.put(sid, userId);
    }

    public static boolean findSessionId(String sid) {
        return sessions.containsKey(sid);
    }

    public static String findUserIdBySid(String sid) {
        return sessions.get(sid);
    }

    public static Collection<String> findAllUserId() {
        return sessions.values();
    }

    public static void removeSession(String sid) {
        sessions.remove(sid);
    }
}
