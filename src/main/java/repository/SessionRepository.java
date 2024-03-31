package repository;

import java.util.Collection;
import java.util.Optional;
import model.Session;

public interface SessionRepository {
    Session addSession(Session session);

    Optional<Session> findSessionBySid(String sid);

    Collection<Session> findAllSession();

    void removeSession(String sid);

    boolean isSessionIdPresent(String sid);
}
