package http.request;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class MethodTest {
    @DisplayName("알맞은 HTTP 요청이면 Method객체를 생성할 수 있다.")
    @ValueSource(strings = {"GET", "get", "GeT", "POST", "post", "POst", "PUT", "put", "PuT"})
    @ParameterizedTest
    void generateValidMethod(String methodCommand) {
        Method method = new Method(methodCommand);
        assertThat(method.getMethodCommand()).isEqualTo(methodCommand.toUpperCase());
    }

    @DisplayName("알맞은 HTTP 요청이 아니면 IllegalStateException을 던진다.")
    @ValueSource(strings = {"zzawang", "lucas", "java"})
    @ParameterizedTest
    void generateInvalidMethod(String methodCommand) {
        assertThatThrownBy(() -> {
            Method method = new Method(methodCommand);
        }).isInstanceOf(IllegalStateException.class);
    }
}