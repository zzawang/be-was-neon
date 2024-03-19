package utils;

import static utils.Constant.SID_MAX_LENGTH;
import static utils.Constant.SID_RANGE_NUM;
import static utils.Constant.SID_START_INDEX;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SidGenerator {
    private static final List<String> sessionIds = new ArrayList<>();

    public static String generate() {
        String sid = generateRandomSid();
        while (!sessionIds.contains(sid)) {
            sid = generateRandomSid();
        }
        sessionIds.add(sid);
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
