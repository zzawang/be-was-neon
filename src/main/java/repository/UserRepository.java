package repository;

import java.util.Collection;
import java.util.Optional;
import model.User;

/**
 * 사용자 데이터베이스에 대한 작업을 정의한 인터페이스
 */
public interface UserRepository {
    /**
     * 새로운 사용자를 추가한다.
     *
     * @param user 추가할 사용자
     * @return 추가된 사용자
     */
    User addUser(User user);

    /**
     * 주어진 사용자 ID에 해당하는 사용자를 반환한다.
     *
     * @param userId 사용자 ID
     * @return 해당 사용자 ID에 대한 Optional 형태의 사용자 객체
     */
    Optional<User> findUserByUserId(String userId);

    /**
     * 모든 사용자를 반환한다.
     *
     * @return 모든 사용자의 컬렉션
     */
    Collection<User> findAllUser();
}
