package utils;

import static utils.Constant.BASE_DIRECTORY_PATH;
import static utils.Constant.BASE_PATH;
import static utils.Constant.EMPTY;
import static utils.Constant.EMPTY_IMG;
import static utils.Constant.IMG_PATH;
import static utils.Constant.LINE_FEED;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import model.Article;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 게시글 생성 유틸리티 클래스
 */
public class ArticleGenerator {
    private static final Logger logger = LoggerFactory.getLogger(ArticleGenerator.class);
    private static final String BOUNDARY_REGEX = "^------WebKitFormBoundary.*$";
    private static final Pattern CONTENT_PATTERN
            = Pattern.compile("^\s*Content-Disposition:\s*form-data;\s*name=\"articleContent\"");
    private static final Pattern IMAGE_PATTERN
            = Pattern.compile("^\s*Content-Disposition:\s*form-data;\s*name=\"articleImage\";\s*filename=\"(.*?)\"");

    /**
     * 게시글을 생성한다.
     *
     * @param body     게시글 데이터
     * @param userName 작성자 이름
     * @return 생성된 Article 객체
     */
    public static Article generateArticle(byte[] body, String userName) {
        String content = "";
        String imageName = "";

        // form data라면 게시글 내용과 이미지 분리
        ByteLineBoundary boundary = new ByteLineBoundary(0, getEndOfByteLine(0, body));
        while (boundary.start < body.length) {
            String convertedStr = readByteLine(boundary, body);
            if (convertedStr.matches(BOUNDARY_REGEX)) {
                String contentInfo = readByteLine(boundary, body);
                if (isArticleContent(contentInfo)) {
                    content = Decoder.decodeStr(getArticleContent(body, boundary));
                }
                if (isArticleImage(contentInfo)) {
                    imageName = getImageName(contentInfo);
                    generateImage(body, imageName, boundary);
                    break;
                }
            }
        }
        return new Article(userName, content, IMG_PATH + BASE_PATH + imageName);
    }

    private static boolean isArticleContent(String contentInfo) {
        Matcher contentMatcher = CONTENT_PATTERN.matcher(contentInfo);
        return contentMatcher.find();
    }

    private static boolean isArticleImage(String contentInfo) {
        Matcher imageMatcher = IMAGE_PATTERN.matcher(contentInfo);
        return imageMatcher.find();
    }

    private static String getArticleContent(byte[] body, ByteLineBoundary boundary) {
        boundary.setNextLine(body);
        ByteLineBoundary preByteLineBoundary = new ByteLineBoundary(boundary.start, boundary.end);
        List<String> content = new ArrayList<>();
        String line = readByteLine(boundary, body);
        while (!line.matches(BOUNDARY_REGEX)) {
            content.add(line);
            preByteLineBoundary.setBoundary(boundary.start, boundary.end);
            line = readByteLine(boundary, body);
        }
        logger.debug("게시글 내용 : " + String.join(LINE_FEED, content));
        boundary.setBoundary(preByteLineBoundary.start, preByteLineBoundary.end); // 다시 boundary 돌려놓기
        return String.join(LINE_FEED, content);
    }

    private static String getImageName(String contentInfo) {
        Matcher imageMatcher = IMAGE_PATTERN.matcher(contentInfo);
        String fileName = EMPTY;
        if (imageMatcher.find()) {
            fileName = imageMatcher.group(1);
        }
        if (fileName.equals(EMPTY)) {
            return EMPTY_IMG;
        }
        return fileName;
    }

    private static void generateImage(byte[] body, String imageName, ByteLineBoundary boundary) {
        boundary.setNextLine(body);
        imageName = Decoder.decodeStr(imageName);
        if (imageName.equals(EMPTY_IMG)) {
            return;
        }
        byte[] image = Arrays.copyOfRange(body, boundary.end + 3, body.length - 2);
        generateFile(imageName, image);
    }

    private static String readByteLine(ByteLineBoundary boundary, byte[] body) {
        boundary.end = getEndOfByteLine(boundary.start, body);
        String convertedStr = convertByteToStr(body, boundary);
        boundary.start = boundary.end + 1;
        return convertedStr;
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

    private static String convertByteToStr(byte[] body, ByteLineBoundary boundary) {
        byte[] copyByte = Arrays.copyOfRange(body, boundary.start, boundary.end - 1);
        return new String(copyByte, StandardCharsets.UTF_8);
    }

    private static class ByteLineBoundary {
        int start;
        int end;

        ByteLineBoundary(int start, int end) {
            this.start = start;
            this.end = end;
        }

        public void setBoundary(int start, int end) {
            this.start = start;
            this.end = end;
        }

        public void setNextLine(byte[] body) {
            this.start += 2;
            this.end = getEndOfByteLine(this.start, body);
        }
    }
}
