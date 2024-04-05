package repository;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import javax.sql.DataSource;
import model.Session;

/**
 * JDBC를 사용하여 세션 데이터베이스에 대한 작업을 구현한 클래스
 */
public class JdbcSessionRepository implements SessionRepository {
    private static final String INSERT_SQL = "INSERT INTO session (sid, user_id, user_name, user_email) VALUES (?, ?, ?, ?)";
    private static final String SELECT_BY_SID_SQL = "SELECT * FROM session WHERE sid = ?";
    private static final String SELECT_ALL_SQL = "SELECT * FROM session";
    private static final String DELETE_BY_SID_SQL = "DELETE FROM session WHERE sid = ?";
    private static final String CHECK_SID_SQL = "SELECT COUNT(*) FROM session WHERE sid = ?";

    private final DataSource dataSource;

    /**
     * JdbcSessionRepository를 생성하는 생성자
     *
     * @param dataSource 데이터베이스 연결을 위한 DataSource
     */
    public JdbcSessionRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Session addSession(Session session) {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, session.getSid());
            pstmt.setString(2, session.getUserId());
            pstmt.setString(3, session.getUserName());
            pstmt.setString(4, session.getUserEmail());
            pstmt.executeUpdate();
            ResultSet rs = pstmt.getGeneratedKeys();

            if (!rs.next()) {
                throw new SQLException("id 조회 실패");
            }
            session.setId(rs.getLong(1));
            return session;
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public Optional<Session> findSessionBySid(String sid) {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SELECT_BY_SID_SQL)) {
            pstmt.setString(1, sid);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(generateSession(rs));
                }
                return Optional.empty();
            }
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public Collection<Session> findAllSession() {
        List<Session> sessions = new ArrayList<>();
        try (Connection conn = dataSource.getConnection();
             PreparedStatement statement = conn.prepareStatement(SELECT_ALL_SQL);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Session session = generateSession(resultSet);
                sessions.add(session);
            }
            return sessions;
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void removeSession(String sid) {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(DELETE_BY_SID_SQL)) {
            pstmt.setString(1, sid);
            pstmt.executeUpdate();
            pstmt.executeUpdate();
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public boolean isSessionIdPresent(String sid) {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(CHECK_SID_SQL)) {
            pstmt.setString(1, sid);
            try (ResultSet resultSet = pstmt.executeQuery()) {
                if (resultSet.next()) {
                    int count = resultSet.getInt(1);
                    return count > 0;
                }
            }
            throw new IllegalStateException();
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    /**
     * ResultSet으로부터 Session 객체를 생성한다.
     *
     * @param resultSet ResultSet 객체
     * @return 생성된 Session 객체
     * @throws SQLException SQL 예외가 발생한 경우
     */
    private Session generateSession(ResultSet resultSet) throws SQLException {
        long id = resultSet.getLong("id");
        String sid = resultSet.getString("sid");
        String user_id = resultSet.getString("user_id");
        String user_name = resultSet.getString("user_name");
        String user_email = resultSet.getString("user_email");
        return new Session(id, sid, user_id, user_name, user_email);
    }
}
