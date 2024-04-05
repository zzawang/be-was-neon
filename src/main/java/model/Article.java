package model;

/**
 * 게시물을 나타내는 클래스
 */
public class Article {
    private static final String ARTICLE_TO_STRING_FORMAT = "Article [sequenceId=%s, userName=%s, content=%s, filePath=%s]";

    private long id;
    private final String userName;
    private final String content;
    private final String filePath;

    /**
     * 게시물 ID를 포함한 Article 클래스의 생성자
     *
     * @param id        게시물 ID
     * @param userName  사용자 이름
     * @param content   게시물 내용
     * @param filePath  이미지 파일 경로
     */
    public Article(long id, String userName, String content, String filePath) {
        this.id = id;
        this.userName = userName;
        this.content = content;
        this.filePath = filePath;
    }

    /**
     * 게시물 ID를 포함하지 않은 Article 클래스의 생성자
     *
     * @param userName  사용자 이름
     * @param content   게시물 내용
     * @param filePath  이미지 파일 경로
     */
    public Article(String userName, String content, String filePath) {
        this.userName = userName;
        this.content = content;
        this.filePath = filePath;
    }

    /**
     * 게시물의 ID를 설정한다.
     *
     * @param id 게시물 ID
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * 게시물의 작성자를 반환한다.
     *
     * @return 사용자 이름
     */
    public String getUserName() {
        return userName;
    }

    /**
     * 게시물의 내용을 반환한다.
     *
     * @return 게시물 내용
     */
    public String getContent() {
        return content;
    }

    /**
     * 게시물의 이미지 파일 경로를 반환한다.
     *
     * @return 이미지 파일 경로
     */
    public String getFilePath() {
        return filePath;
    }


    /**
     * 객체를 문자열로 표현한다.
     *
     * @return 알맞은 형식의 문자열로 표현한 Article 객체
     */
    @Override
    public String toString() {
        return String.format(ARTICLE_TO_STRING_FORMAT, id, userName, content, filePath);
    }
}