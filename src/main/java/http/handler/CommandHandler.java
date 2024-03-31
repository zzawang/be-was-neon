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
import repository.JdbcSessionRepository;
import repository.JdbcUserRepository;
import utils.DirectoryMatcher;
import utils.StaticFileReader;

public abstract class CommandHandler {
    protected RequestManager requestManager;
    protected ResponseManager responseManager;
    protected static JdbcUserRepository userDb = new JdbcUserRepository(createDataSource());
    protected static JdbcSessionRepository sessionDb = new JdbcSessionRepository(createDataSource());
    protected static JdbcArticleRepository articleDb = new JdbcArticleRepository(createDataSource());
    protected SessionManager sessionManager;

    public void setManagers(RequestManager requestManager, ResponseManager responseManager) {
        this.requestManager = requestManager;
        this.responseManager = responseManager;
        this.sessionManager = new SessionManager(requestManager, sessionDb);
    }

    public void handleGetRequest() {
        responseManager.setErrorResponse(Status.BAD_REQUEST);
    }

    public void handlePostRequest() {
        responseManager.setErrorResponse(Status.BAD_REQUEST);
    }

    public void handlePutRequest() {
        responseManager.setErrorResponse(Status.BAD_REQUEST);
    }

    public void handleDeleteRequest() {
        responseManager.setErrorResponse(Status.BAD_REQUEST);
    }

    public void handleHeadRequest() {
        responseManager.setErrorResponse(Status.BAD_REQUEST);
    }

    public void handleConnectRequest() {
        responseManager.setErrorResponse(Status.BAD_REQUEST);
    }

    public void handleTraceRequest() {
        responseManager.setErrorResponse(Status.BAD_REQUEST);
    }

    public void handlePatchRequest() {
        responseManager.setErrorResponse(Status.BAD_REQUEST);
    }

    private static DataSource createDataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName("org.h2.Driver");
        dataSource.setUrl("jdbc:h2:tcp://localhost/~/was");
        dataSource.setUsername("zzawang");
        dataSource.setPassword(EMPTY);
        return dataSource;
    }

    protected void serveHtmlFileFromDirectory(String path) {
        String filePathUrl = DirectoryMatcher.matchDirectory(path);
        StaticFileReader staticFileReader = generateStaticFileReader(path);
        byte[] responseBody = staticFileReader.readAllBytes();
        ContentType contentType = getContentType(filePathUrl);
        responseManager.setOkResponse(contentType, responseBody);
    }

    protected StaticFileReader generateStaticFileReader(String path) {
        String filePathUrl = DirectoryMatcher.matchDirectory(path);
        File file = new File(filePathUrl);
        return new StaticFileReader(file);
    }

    protected ContentType getContentType(String filePathUrl) {
        String[] parts = filePathUrl.split(CONTENT_TYPE_DELIMITER);
        String type = parts[parts.length - 1].toLowerCase();

        for (ContentType contentType : ContentType.values()) {
            if (contentType.name().equals(type)) {
                return contentType;
            }
        }
        throw new IllegalArgumentException();
    }
}