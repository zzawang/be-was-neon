package utils;

import static utils.Constant.BASE_DIRECTORY_PATH;
import static utils.Constant.BASE_PATH;
import static utils.Constant.IMG_PATH;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import model.Article;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ArticleGenerator {
    private static final Logger logger = LoggerFactory.getLogger(ArticleGenerator.class);
    private static final String BOUNDARY_REGEX = "^------WebKitFormBoundary.*$";
    private static final Pattern CONTENT_PATTERN
            = Pattern.compile("^\s*Content-Disposition:\s*form-data;\s*name=\"articleContent\"");
    private static final Pattern IMAGE_PATTERN
            = Pattern.compile("^\s*Content-Disposition:\s*form-data;\s*name=\"articleImage\";\s*filename=\"(.*?)\"");

    public static Article generateArticle(byte[] body, String userName) {
        String content = "";
        String imageName = "";
        // form data라면 게시글 내용과 이미지 분리
        int start = 0;
        while (start < body.length) {
            int end = getEndOfByteLine(start, body);
            String convertedStr = convertByteToStr(body, start, end);
            start = end + 1;

            if (convertedStr.matches(BOUNDARY_REGEX)) {
                end = getEndOfByteLine(start, body);
                String contentInfo = convertByteToStr(body, start, end);
                start = end + 1;

                Matcher contentMatcher = CONTENT_PATTERN.matcher(contentInfo);
                if (contentMatcher.find()) {
                    start += 2;
                    end = getEndOfByteLine(start, body);

                    content = convertByteToStr(body, start, end);
                    logger.info("게시글 내용 : " + content);
                }

                Matcher imageMatcher = IMAGE_PATTERN.matcher(contentInfo);
                if (imageMatcher.find()) {
                    start += 2;
                    end = getEndOfByteLine(start, body);

                    imageName = Decoder.decodeStr(imageMatcher.group(1));
                    byte[] image = Arrays.copyOfRange(body, end + 3, body.length - 2);
                    generateFile(imageName, image);
                    break;
                }
            }
        }

        return new Article(userName, content, IMG_PATH + BASE_PATH + imageName);
    }

    private static void generateFile(String imageName, byte[] image) {
        String filePath = BASE_DIRECTORY_PATH + IMG_PATH + BASE_PATH + imageName; // 파일 경로 및 이름을 지정

        try (FileOutputStream fos = new FileOutputStream(filePath)) {
            fos.write(image);
            logger.debug("이미지 파일이 성공적으로 생성되었습니다.");
        } catch (IOException e) {
            logger.error("파일 생성 중 오류가 발생했습니다: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static int getEndOfByteLine(int start, byte[] body) {
        int end = start;
        for (int index = start; index < body.length; index++) {
            if (body[index] == 10) { // \n 이라면
                end = index;
                break;
            }
        }

        if (end == start) {
            return body.length;
        }
        return end;
    }

    private static String convertByteToStr(byte[] body, int start, int end) {
        byte[] copyByte = Arrays.copyOfRange(body, start, end - 1);
        return new String(copyByte, StandardCharsets.UTF_8);
    }
}
