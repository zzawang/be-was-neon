package http.handler;

import utils.Decoder;

public class FileHandler extends CommandHandler {
    @Override
    public void handleGetRequest() {
        String filePathUrl = Decoder.decodeStr(requestManager.getFilePath().getFilePath());
        serveHtmlFileFromDirectory(filePathUrl);
    }
}
