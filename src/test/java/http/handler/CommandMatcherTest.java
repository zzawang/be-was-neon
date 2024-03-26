package http.handler;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.FileNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CommandMatcherTest {

    @DisplayName("알맞은 요청인지 확인한다.")
    @Test
    void isValidCommand() {
        assertThat(CommandMatcher.isValidCommand("/user/create")).isTrue();
        assertThat(CommandMatcher.isValidCommand("/userCreate")).isFalse();
    }

    @DisplayName("요청에 따른 알맞은 Handler와 매치될 수 있다.")
    @Test
    void matchCommandHandler() throws FileNotFoundException {
        assertThat(CommandMatcher.matchCommandHandler("/user/create")).isInstanceOf(UserCreateHandler.class);
        assertThat(CommandMatcher.matchCommandHandler("/user/login")).isInstanceOf(UserLoginHandler.class);
    }
}