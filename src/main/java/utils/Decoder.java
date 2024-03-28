package utils;

import static utils.Constant.CHARSETS;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Decoder {
    private static final Logger logger = LoggerFactory.getLogger(Decoder.class);

    public static String[] decodeUser(String[] userEncodedInfo) throws UnsupportedEncodingException {
        String[] userInfo = new String[userEncodedInfo.length];
        for (int index = 0; index < userInfo.length; index++) {
            userInfo[index] = decode(userEncodedInfo[index]);
        }
        return userInfo;
    }

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
