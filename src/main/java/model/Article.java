package model;

public class Article {
    private static final String ARTICLE_TO_STRING_FORMAT = "Article [sequenceId=%s, userName=%s, content=%s, imageSize=%s]";

    private long id;
    private final String userName;
    private final String content;
    private final String filePath;

    public Article(long id, String userName, String content, String filePath) {
        this.id = id;
        this.userName = userName;
        this.content = content;
        this.filePath = filePath;
    }

    public Article(String userName, String content, String filePath) {
        this.userName = userName;
        this.content = content;
        this.filePath = filePath;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public String getContent() {
        return content;
    }

    public String getFilePath() {
        return filePath;
    }

    @Override
    public String toString() {
        return String.format(ARTICLE_TO_STRING_FORMAT, id, userName, content, filePath);
    }
}