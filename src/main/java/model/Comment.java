package model;

public class Comment {
    private static final String COMMENT_TO_STRING_FORMAT = "Comment [sequenceId=%s, articleId=%s]";

    private long id;
    private long aid;
    private String content;

    public Comment(long id, long aid, String content) {
        this.id = id;
        this.aid = aid;
        this.content = content;
    }

    public Comment(long aid, String content) {
        this.aid = aid;
        this.content = content;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setAid(long aid) {
        this.aid = aid;
    }

    public long getId() {
        return id;
    }

    public long getAid() {
        return aid;
    }

    public String getContent() {
        return content;
    }

    @Override
    public String toString() {
        return String.format(COMMENT_TO_STRING_FORMAT, id, aid);
    }
}
