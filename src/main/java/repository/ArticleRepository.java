package repository;

import java.util.Collection;
import java.util.Optional;
import model.Article;

public interface ArticleRepository {
    Article addArticle(Article article);

    long getRecentAid();

    Optional<Article> findArticleByAid(long aid);

    Collection<Article> findAllArticles();
}
