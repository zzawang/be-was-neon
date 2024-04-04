package repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.sql.DataSource;
import model.Comment;

/**
 * JDBC를 사용하여 CommentRepository를 구현한 클래스
 */
public class JdbcCommentRepository implements CommentRepository {
    private static final String INSERT_SQL = "INSERT INTO comment (aid, content) VALUES (?, ?)";
    private static final String SELECT_BY_AID_SQL = "SELECT * FROM comment WHERE aid = ?";
    private static final String SELECT_ALL_SQL = "SELECT * FROM comment";

    private final DataSource dataSource;

    /**
     * JdbcCommentRepository를 생성하는 생성자
     *
     * @param dataSource 데이터베이스 연결을 위한 DataSource
     */
    public JdbcCommentRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Comment addComment(Comment comment) {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setLong(1, comment.getAid());
            pstmt.setString(2, comment.getContent());
            pstmt.executeUpdate();
            ResultSet rs = pstmt.getGeneratedKeys();

            if (!rs.next()) {
                throw new SQLException("id 조회 실패");
            }
            comment.setId(rs.getLong(1));
            return comment;
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public Collection<Comment> findAllCommentsByAid(long aid) {
        List<Comment> comments = new ArrayList<>();
        try (Connection conn = dataSource.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SELECT_BY_AID_SQL)) {
            pstmt.setLong(1, aid);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    comments.add(generateComment(rs));
                }
            }
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
        return comments;
    }

    @Override
    public Collection<Comment> findAllComments() {
        List<Comment> comments = new ArrayList<>();
        try (Connection conn = dataSource.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SELECT_ALL_SQL);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                Comment comment = generateComment(rs);
                comments.add(comment);
            }
            return comments;
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    /**
     * ResultSet으로부터 Comment 객체를 생성한다.
     *
     * @param rs ResultSet 객체
     * @return 생성된 Comment 객체
     * @throws SQLException SQL 예외가 발생한 경우
     */
    private Comment generateComment(ResultSet rs) throws SQLException {
        long id = rs.getLong("id");
        long aid = rs.getLong("aid");
        String content = rs.getString("content");
        return new Comment(id, aid, content);
    }
}