package model;

public class Article {
    private static final String ARTICLE_TO_STRING_FORMAT = "Article [sequenceId=%s, userName=%s, content=%s, imageSize=%s]";

    private String sequenceId;
    private final String userName;
    private final String content;
    private final String filePath;

    public Article(String userName, String content, String filePath) {
        this.userName = userName;
        this.content = content;
        this.filePath = filePath;
    }

    public void setSequenceId(String sequenceId) {
        this.sequenceId = sequenceId;
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
        return String.format(ARTICLE_TO_STRING_FORMAT, sequenceId, userName, content, filePath);
    }
}