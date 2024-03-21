package utils;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class SidGeneratorTest {

    @DisplayName("각 자리의 숫자가 0부터 9 사이인 6자리의 숫자 문자열을 생성한다.")
    @Test
    void generate() {
        String sid = SidGenerator.generate();
        assertThat(sid).matches("\\d{6}");
    }
}