package session;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SessionMemory {
    private static Map<String, String> sessions = new ConcurrentHashMap<>();

    public static void addSession(String sid, String userName) {
        sessions.put(sid, userName);
    }

    public static boolean findSessionId(String sid) {
        return sessions.containsKey(sid);
    }

    public static String findUserNameBySid(String sid) {
        return sessions.get(sid);
    }

    public static Collection<String> findAllUserName() {
        return sessions.values();
    }

    public static void removeSession(String sid) {
        sessions.remove(sid);
    }
}
