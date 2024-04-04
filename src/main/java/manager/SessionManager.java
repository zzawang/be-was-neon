package manager;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import model.Session;
import model.User;
import repository.JdbcSessionRepository;

/**
 * 세션을 관리하는 클래스
 */
public class SessionManager {
    private final RequestManager requestManager;
    private final JdbcSessionRepository sessionDb;

    /**
     * SessionManager 클래스의 생성자
     *
     * @param requestManager request를 관리하는 객체
     * @param sessionDb      세션 데이터베이스 구현체
     */
    public SessionManager(RequestManager requestManager, JdbcSessionRepository sessionDb) {
        this.requestManager = requestManager;
        this.sessionDb = sessionDb;
    }


    /**
     * 사용자가 인증되었는지 여부를 확인한다.
     *
     * @return 사용자가 인증되었는지 여부
     */
    public boolean isAuthorizedUser() {
        Optional<String> optCookie = requestManager.getCookie();
        if (optCookie.isEmpty()) {
            return false;
        }
        String sid = requestManager.getSid();
        if (isSessionIdExists(sid)) {
            return true;
        }
        return false;
    }

    /**
     * 사용자 이름을 가져온다.
     *
     * @return 사용자 이름
     */
    public String getUserName() {
        String sid = requestManager.getSid();
        Optional<Session> session = sessionDb.findSessionBySid(sid);
        if (session.isEmpty()) {
            return "UNKNOWN";
        }
        return session.get().getUserName();
    }

    /**
     * 모든 사용자를 가져온다.
     *
     * @return 사용자 목록
     */
    public List<Session> getUsers() {
        return sessionDb.findAllSession().stream().toList();
    }

    /**
     * 세션을 만료시킨다.
     */
    public void expireSession() {
        String sid = requestManager.getSid();
        sessionDb.removeSession(sid);
    }

    /**
     * 세션을 설정한다.
     *
     * @param user 사용자
     * @return 생성된 세션 ID
     */
    public String setSession(User user) {
        String sid = generate();
        Session session = new Session(sid, user.getUserId(), user.getUserName(), user.getUserEmail());
        sessionDb.addSession(session);
        return sid;
    }

    private String generate() {
        String sid = generateRandomSid();
        while (isSessionIdExists(sid)) {
            sid = generateRandomSid();
        }
        return sid;
    }

    private String generateRandomSid() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }

    private boolean isSessionIdExists(String sid) {
        return sessionDb.isSessionIdPresent(sid);
    }
}
