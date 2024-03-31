package repository;

import java.util.Collection;
import java.util.Optional;
import model.User;

public interface UserRepository {
    User addUser(User user);

    Optional<User> findUserByUserId(String userId);

    Collection<User> findAllUser();
}
