package model;

/**
 * 댓글을 나타내는 클래스
 */
public class Comment {
    private static final String COMMENT_TO_STRING_FORMAT = "Comment [sequenceId=%s, articleId=%s]";

    private long id;
    private long aid;
    private String content;

    /**
     * 댓글 ID를 포함한 Comment 클래스의 생성자
     *
     * @param id      댓글 ID
     * @param aid     게시물 ID
     * @param content 댓글 내용
     */
    public Comment(long id, long aid, String content) {
        this.id = id;
        this.aid = aid;
        this.content = content;
    }

    /**
     * 댓글 ID를 포함하지 않은 Comment 클래스의 생성자
     *
     * @param aid     게시물 ID
     * @param content 댓글 내용
     */
    public Comment(long aid, String content) {
        this.aid = aid;
        this.content = content;
    }

    /**
     * 댓글의 ID를 설정한다.
     *
     * @param id 댓글 ID
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * 댓글의 게시물 ID를 설정한다.
     *
     * @param aid 게시물 ID
     */
    public void setAid(long aid) {
        this.aid = aid;
    }

    /**
     * 댓글의 ID를 반환한다.
     *
     * @return 댓글 ID
     */
    public long getId() {
        return id;
    }

    /**
     * 댓글의 게시물 ID를 반환한다.
     *
     * @return 게시물 ID
     */
    public long getAid() {
        return aid;
    }

    /**
     * 댓글의 내용을 반환한다.
     *
     * @return 댓글 내용
     */
    public String getContent() {
        return content;
    }

    /**
     * 객체를 문자열로 표현한다.
     *
     * @return 알맞은 형식의 문자열로 표현한 Comment 객체
     */
    @Override
    public String toString() {
        return String.format(COMMENT_TO_STRING_FORMAT, id, aid);
    }
}
