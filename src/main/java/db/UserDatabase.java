package db;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import model.User;

public class UserDatabase {
    private static Map<String, User> users = new ConcurrentHashMap<>();

    public static void addUser(User user) {
        users.put(user.getName(), user);
    }

    public static User findUserById(String userId) {
        return users.values().stream()
                .filter(user -> user.getId().equals(userId))
                .findFirst()
                .orElse(null); // 해당 ID를 가진 사용자를 찾을 수 없을 경우 null 반환
    }

    public static User findUserByName(String userName) {
        return users.get(userName);
    }

    public static Collection<User> findAll() {
        return users.values();
    }
}
