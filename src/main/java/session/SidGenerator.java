package session;

import java.util.UUID;

public class SidGenerator {
    public static String generate() {
        String sid = generateRandomSid();
        while (SessionManager.isSessionIdExists(sid)) {
            sid = generateRandomSid();
        }
        return sid;
    }

    private static String generateRandomSid() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }
}
