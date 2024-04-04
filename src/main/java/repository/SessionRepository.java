package repository;

import java.util.Collection;
import java.util.Optional;
import model.Session;

/**
 * 세션 데이터베이스에 대한 작업을 정의한 인터페이스
 */
public interface SessionRepository {
    /**
     * 새로운 세션을 추가한다.
     *
     * @param session 추가할 세션
     * @return 추가된 세션
     */
    Session addSession(Session session);

    /**
     * 주어진 세션 ID에 해당하는 세션을 반환한다.
     *
     * @param sid 세션 ID
     * @return 해당 세션 ID에 대한 Optional 형태의 세션 객체
     */
    Optional<Session> findSessionBySid(String sid);

    /**
     * 모든 세션을 반환한다.
     *
     * @return 모든 세션의 컬렉션
     */
    Collection<Session> findAllSession();

    /**
     * 주어진 세션 ID에 해당하는 세션을 제거한다.
     *
     * @param sid 세션 ID
     */
    void removeSession(String sid);

    /**
     * 주어진 세션 ID가 데이터베이스에 존재하는지 여부를 반환한다.
     *
     * @param sid 세션 ID
     * @return 세션 ID의 존재 여부
     */
    boolean isSessionIdPresent(String sid);
}
