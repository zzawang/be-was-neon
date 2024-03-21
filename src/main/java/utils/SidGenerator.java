package utils;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class SidGenerator {
    private static final int SID_START_INDEX = 0;
    private static final int SID_MAX_LENGTH = 6;
    private static final int SID_RANGE_NUM = 10;

    private static final Set<String> sessionIds = new HashSet<>();

    public static String generate() {
        String sid = generateRandomSid();
        while (!sessionIds.add(sid)) {
            sid = generateRandomSid();
        }
        return sid;
    }

    private static String generateRandomSid() {
        Random random = new Random();

        StringBuilder sb = new StringBuilder();
        for (int i = SID_START_INDEX; i < SID_MAX_LENGTH; i++) {
            sb.append(random.nextInt(SID_RANGE_NUM));  // 0부터 9 사이의 숫자를 생성하여 문자열에 추가
        }
        return sb.toString();
    }
}
