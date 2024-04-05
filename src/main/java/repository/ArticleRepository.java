package repository;

import java.util.Collection;
import java.util.Optional;
import model.Article;

/**
 * 게시글 데이터베이스에 대한 작업을 정의한 인터페이스
 */
public interface ArticleRepository {
    /**
     * 새로운 게시글을 추가한다.
     *
     * @param article 추가할 게시글
     * @return 추가된 게시글
     */
    Article addArticle(Article article);

    /**
     * 가장 최근 게시글의 ID를 반환한다.
     *
     * @return 최근 게시글의 ID
     */
    long getRecentAid();

    /**
     * 주어진 ID에 해당하는 게시글을 찾아서 반환한다.
     *
     * @param aid 게시글 ID
     * @return Optional 형태의 게시글 객체
     */
    Optional<Article> findArticleByAid(long aid);


    /**
     * 모든 게시글을 반환한다.
     *
     * @return 모든 게시글의 컬렉션
     */
    Collection<Article> findAllArticles();
}
