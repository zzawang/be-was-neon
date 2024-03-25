package utils;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class DirectoryMatcherTest {
    @DisplayName("파일을 찾아갈 수 있도록 부모 디렉토리 위치를 추가해준다.")
    @Test
    void mathDirectory() {
        assertThat(DirectoryMatcher.matchUnknownEndPoint("/")).isEqualTo(
                "src/main/resources/static/index.html");
        assertThat(DirectoryMatcher.matchUnknownEndPoint("/registration")).isEqualTo(
                "src/main/resources/static/registration/register.html");
        assertThat(DirectoryMatcher.matchUnknownEndPoint("/login")).isEqualTo(
                "src/main/resources/static/login/login.html");
        assertThat(DirectoryMatcher.matchUnknownEndPoint("/reset.css")).isEqualTo(
                "src/main/resources/static/reset.css");
        assertThat(DirectoryMatcher.matchUnknownEndPoint("/img/like.svg")).isEqualTo(
                "src/main/resources/static/img/like.svg");
    }
}