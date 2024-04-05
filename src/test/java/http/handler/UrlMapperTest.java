package http.handler;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.FileNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class UrlMapperTest {

    @DisplayName("알맞은 요청인지 확인한다.")
    @Test
    void isValidCommand() {
        assertThat(UrlMapper.isValidCommand("/registration")).isTrue();
        assertThat(UrlMapper.isValidCommand("/java")).isFalse();
    }

    @DisplayName("요청에 따른 알맞은 Handler와 매치될 수 있다.")
    @Test
    void matchCommandHandler() throws FileNotFoundException {
        assertThat(UrlMapper.matchCommandHandler("/registration")).isInstanceOf(UserCreateHandler.class);
        assertThat(UrlMapper.matchCommandHandler("/login")).isInstanceOf(UserLoginHandler.class);
    }
}