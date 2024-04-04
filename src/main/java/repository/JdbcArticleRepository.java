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
import model.Article;

/**
 * JDBC를 사용하여 ArticleRepository를 구현한 클래스
 */
public class JdbcArticleRepository implements ArticleRepository {
    private static final String INSERT_SQL = "INSERT INTO article (user_name, content, filePath) VALUES (?, ?, ?)";
    private static final String SELECT_BY_AID_SQL = "SELECT * FROM article WHERE id = ?";
    private static final String SELECT_ALL_SQL = "SELECT * FROM article";
    private static final String SELECT_RECENT_AID_SQL = "SELECT MAX(id) FROM article";

    private final DataSource dataSource;

    /**
     * JdbcArticleRepository를 생성하는 생성자
     *
     * @param dataSource 데이터베이스 연결을 위한 DataSource
     */
    public JdbcArticleRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Article addArticle(Article article) {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, article.getUserName());
            pstmt.setString(2, article.getContent());
            pstmt.setString(3, article.getFilePath());
            pstmt.executeUpdate();
            ResultSet rs = pstmt.getGeneratedKeys();

            if (!rs.next()) {
                throw new SQLException("id 조회 실패");
            }
            article.setId(rs.getLong(1));
            return article;
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public long getRecentAid() {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SELECT_RECENT_AID_SQL);
             ResultSet rs = pstmt.executeQuery()) {
            if (!rs.next()) {
                throw new SQLException("id 조회 실패");
            }
            return rs.getLong(1);
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public Optional<Article> findArticleByAid(long aid) {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SELECT_BY_AID_SQL)) {
            pstmt.setLong(1, aid);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(generateArticle(rs));
                }
                return Optional.empty();
            }
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public Collection<Article> findAllArticles() {
        List<Article> articles = new ArrayList<>();
        try (Connection conn = dataSource.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SELECT_ALL_SQL);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                Article article = generateArticle(rs);
                articles.add(article);
            }
            return articles;
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    /**
     * ResultSet으로부터 Article 객체를 생성한다.
     *
     * @param rs ResultSet 객체
     * @return 생성된 Article 객체
     * @throws SQLException SQL 예외가 발생한 경우
     */
    private Article generateArticle(ResultSet rs) throws SQLException {
        long id = rs.getLong("id");
        String userName = rs.getString("user_name");
        String content = rs.getString("content");
        String filePath = rs.getString("filePath");
        return new Article(id, userName, content, filePath);
    }
}