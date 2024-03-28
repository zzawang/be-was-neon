package db;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import model.Article;

public class ArticleDatabase {
    private static int sequenceId = 0; // 순서 인덱스. 최신 게시물일수록 숫자가 크다.

    private static Map<String, Article> articles = new ConcurrentHashMap<>();

    public static void addArticle(Article article) {
        String key = String.valueOf(++sequenceId);
        article.setSequenceId(key);
        articles.put(key, article);
    }

    public static String getRecentSequenceId() {
        return String.valueOf(sequenceId);
    }

    public static Article findArticleBySequenceId(String sequenceId) {
        return articles.get(sequenceId);
    }

    public static boolean hasArticleId(String sequenceId) {
        return articles.containsKey(sequenceId);
    }

    public static Collection<Article> findAllArticles() {
        return articles.values();
    }

    public static boolean isEmpty() {
        return sequenceId == 0;
    }
}
