package http.handler;

import utils.Decoder;

/**
 * 특정 요청(/login, /main)이 아닌 요청에 대한 정적 파일을 제공하는 클래스
 * CommandHandler 클래스의 handleGetRequest 메서드를 오버라이드하여 파일을 제공하는 방법을 구현한다.
 */
public class FileHandler extends CommandHandler {
    /**
     * 특정 url의 파일을 제공한다.
     */
    @Override
    public void handleGetRequest() {
        // 요청된 파일 경로가 한글일 수 있으므로 디코딩한다.
        String filePathUrl = Decoder.decodeStr(requestManager.getFilePath().getFilePath());
        serveFileFromDirectory(filePathUrl);
    }
}
