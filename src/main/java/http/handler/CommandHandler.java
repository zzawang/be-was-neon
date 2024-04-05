package http.handler;

import static utils.Constant.CONTENT_TYPE_DELIMITER;
import static utils.Constant.EMPTY;

import http.response.ContentType;
import http.response.Status;
import java.io.File;
import javax.sql.DataSource;
import manager.RequestManager;
import manager.ResponseManager;
import manager.SessionManager;
import org.apache.commons.dbcp2.BasicDataSource;
import repository.JdbcArticleRepository;
import repository.JdbcCommentRepository;
import repository.JdbcSessionRepository;
import repository.JdbcUserRepository;
import utils.DirectoryMatcher;
import utils.StaticFileReader;

/**
 * HTTP 요청을 처리하는 추상 클래스
 * 각각의 HTTP 요청에 대해 처리하고 response 설정 형식을 제공한다.
 * 하위 클래스는 각 HTTP 요청 메서드에 대한 구체적인 핸들러를 구현해야 한다.
 */
public abstract class CommandHandler {

    /**
     * 수신된 HTTP 요청을 관리하는 RequestManager
     */
    protected RequestManager requestManager;

    /**
     * 송신되는 HTTP 응답을 관리하는 ResponseManager
     */
    protected ResponseManager responseManager;

    /**
     * 사용자 관련 데이터베이스 작업을 관리하는 UserRepository
     */
    protected static JdbcUserRepository userDb = new JdbcUserRepository(createDataSource());

    /**
     * 세션 관련 데이터베이스 작업을 관리하는 SessionRepository
     */
    protected static JdbcSessionRepository sessionDb = new JdbcSessionRepository(createDataSource());

    /**
     * 기사 관련 데이터베이스 작업을 관리하는 ArticleRepository
     */
    protected static JdbcArticleRepository articleDb = new JdbcArticleRepository(createDataSource());

    /**
     * 댓글 관련 데이터베이스 작업을 관리하는 CommentRepository
     */
    protected static JdbcCommentRepository commentDb = new JdbcCommentRepository(createDataSource());

    /**
     * 세션 관련 작업을 관리하는 SessionManager
     */
    protected SessionManager sessionManager;

    /**
     * RequestManager와 ResponseManager 인스턴스를 설정한다.
     *
     * @param requestManager  RequestManager 인스턴스
     * @param responseManager ResponseManager 인스턴스
     */
    public void setManagers(RequestManager requestManager, ResponseManager responseManager) {
        this.requestManager = requestManager;
        this.responseManager = responseManager;
        this.sessionManager = new SessionManager(requestManager, sessionDb);
    }

    /**
     * HTTP GET 요청을 처리
     */
    public void handleGetRequest() {
        responseManager.setErrorResponse(Status.BAD_REQUEST);
    }

    /**
     * HTTP POST 요청을 처리
     */
    public void handlePostRequest() {
        responseManager.setErrorResponse(Status.BAD_REQUEST);
    }

    /**
     * HTTP PUT 요청을 처리
     */
    public void handlePutRequest() {
        responseManager.setErrorResponse(Status.BAD_REQUEST);
    }

    /**
     * HTTP DELETE 요청을 처리
     */
    public void handleDeleteRequest() {
        responseManager.setErrorResponse(Status.BAD_REQUEST);
    }

    /**
     * HTTP HEAD 요청을 처리
     */
    public void handleHeadRequest() {
        responseManager.setErrorResponse(Status.BAD_REQUEST);
    }

    /**
     * HTTP CONNECT 요청을 처리
     */
    public void handleConnectRequest() {
        responseManager.setErrorResponse(Status.BAD_REQUEST);
    }

    /**
     * HTTP TRACE 요청을 처리
     */
    public void handleTraceRequest() {
        responseManager.setErrorResponse(Status.BAD_REQUEST);
    }

    /**
     * HTTP PATCH 요청을 처리
     */
    public void handlePatchRequest() {
        responseManager.setErrorResponse(Status.BAD_REQUEST);
    }

    /**
     * DataSource 인스턴스를 생성하여 반환한다.
     *
     * @return 필요한 정보로 구성된 DataSource 인스턴스.
     */
    private static DataSource createDataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName("org.h2.Driver");
        dataSource.setUrl("jdbc:h2:tcp://localhost/~/mywas");
        dataSource.setUsername("zzawang");
        dataSource.setPassword(EMPTY);
        return dataSource;
    }

    /**
     * 지정된 디렉토리 경로에서 있는 파일을 제공한다.
     *
     * @param path 파일이 있는 디렉토리 경로
     */
    protected void serveFileFromDirectory(String path) {
        String filePathUrl = DirectoryMatcher.matchDirectory(path);
        StaticFileReader staticFileReader = generateStaticFileReader(path);
        byte[] responseBody = staticFileReader.readAllBytes();
        ContentType contentType = getContentType(filePathUrl);
        responseManager.setOkResponse(contentType, responseBody);
    }

    /**
     * 정적 파일을 읽는 StaticFileReader 인스턴스를 생성한다.
     *
     * @param path 파일이 있는 디렉토리 경로
     * @return StaticFileReader 인스턴스
     */
    protected StaticFileReader generateStaticFileReader(String path) {
        String filePathUrl = DirectoryMatcher.matchDirectory(path);
        File file = new File(filePathUrl);
        return new StaticFileReader(file);
    }

    /**
     * 파일의 확장자를 기반으로 콘텐츠 유형을 결정한다.
     *
     * @param filePathUrl 확장자까지 포함하는 파일의 URL
     * @return 콘텐츠 유형을 나타내는 ContentType Enum값
     * @throws IllegalArgumentException 콘텐츠 유형을 결정할 수 없는 경우 발생한다.
     */
    protected ContentType getContentType(String filePathUrl) throws IllegalArgumentException {
        String[] parts = filePathUrl.split(CONTENT_TYPE_DELIMITER);
        String type = parts[parts.length - 1].toLowerCase();

        for (ContentType contentType : ContentType.values()) {
            if (contentType.name().equals(type)) {
                return contentType;
            }
        }
        throw new IllegalArgumentException("알 수 없는 콘텐츠 유형");
    }
}