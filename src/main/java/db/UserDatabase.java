package db;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import model.User;

public class UserDatabase {
    private static Map<String, User> users = new ConcurrentHashMap<>();

    public static void addUser(User user) {
        users.put(user.getId(), user);
    }

    public static User findUserByName(String userName) {
        return users.values().stream()
                .filter(user -> user.getName().equals(userName))
                .findFirst()
                .orElse(null); // 해당 name을 가진 사용자를 찾을 수 없을 경우 null 반환
    }

    public static User findUserById(String userId) {
        return users.get(userId);
    }

    public static Collection<User> findAll() {
        return users.values();
    }
}
