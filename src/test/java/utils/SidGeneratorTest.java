package utils;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import session.SidGenerator;

class SidGeneratorTest {

    @DisplayName("session Id가 유효한 UUID인지 확인한다.")
    @Test
    void generate() {
        String sid = SidGenerator.generate();
        assertThat(isValidUUID(sid)).isTrue();
    }

    private boolean isValidUUID(String uuidStr) {
        try {
            UUID.fromString(uuidStr);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}