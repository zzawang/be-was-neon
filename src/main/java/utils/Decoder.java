package utils;

import static utils.Constant.CHARSETS;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 문자열 디코딩 관련 유틸리티 클래스
 */
public class Decoder {
    private static final Logger logger = LoggerFactory.getLogger(Decoder.class);

    /**
     * 사용자 정보를 디코딩한다.
     * @param userEncodedInfo 인코딩된 사용자 정보 배열
     * @return 디코딩된 사용자 정보 배열
     * @throws UnsupportedEncodingException 인코딩이 지원되지 않는 경우 발생한다.
     */
    public static String[] decodeUser(String[] userEncodedInfo) throws UnsupportedEncodingException {
        String[] userInfo = new String[userEncodedInfo.length];
        for (int index = 0; index < userInfo.length; index++) {
            userInfo[index] = decode(userEncodedInfo[index]);
        }
        return userInfo;
    }

    /**
     * 문자열을 디코딩한다.
     * @param str 디코딩할 문자열
     * @return 디코딩된 문자열
     */
    public static String decodeStr(String str) {
        String result = "";
        try {
            result = Decoder.decode(str);
        } catch (UnsupportedEncodingException e) {
            logger.error(e.getMessage());
        }
        return result;
    }

    private static String decode(String originalStr) throws UnsupportedEncodingException {
        return URLDecoder.decode(originalStr, CHARSETS);
    }
}
