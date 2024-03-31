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
import model.User;

public class JdbcUserRepository implements UserRepository {
    private static final String INSERT_SQL = "INSERT INTO users (user_id, user_pw, user_name, user_email) VALUES (?, ?, ?, ?)";
    private static final String SELECT_BY_USER_ID_SQL = "SELECT * FROM users WHERE user_id = ?";
    private static final String SELECT_ALL_SQL = "SELECT * FROM users";

    private final DataSource dataSource;

    public JdbcUserRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public User addUser(User user) {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, user.getUserId());
            pstmt.setString(2, user.getUserPw());
            pstmt.setString(3, user.getUserName());
            pstmt.setString(4, user.getUserEmail());
            pstmt.executeUpdate();
            ResultSet rs = pstmt.getGeneratedKeys();

            if (!rs.next()) {
                throw new SQLException("id 조회 실패");
            }
            user.setId(rs.getLong(1));
            return user;
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public Optional<User> findUserByUserId(String userId) {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SELECT_BY_USER_ID_SQL)) {
            pstmt.setString(1, userId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(generateUser(rs));
                }
                return Optional.empty();
            }
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public Collection<User> findAllUser() {
        List<User> users = new ArrayList<>();
        try (Connection conn = dataSource.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SELECT_ALL_SQL);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                User user = generateUser(rs);
                users.add(user);
            }
            return users;
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    private User generateUser(ResultSet rs) throws SQLException {
        String user_id = rs.getString("user_id");
        String user_pw = rs.getString("user_pw");
        String user_name = rs.getString("user_name");
        String user_email = rs.getString("user_email");
        return new User(user_id, user_pw, user_name, user_email);
    }
}
