package http;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class HeadersTest {
    private static final String HEADERS = """
            Host: localhost:8080
            Connection: keep-alive
            Content-Length: 59
            Content-Type: application/x-www-form-urlencoded
            Accept: */*
            """;
    private Headers headers;

    @BeforeEach
    void setUp() {
        this.headers = new Headers();
        this.headers.setHeaders(HEADERS);
    }

    @DisplayName("원하는 Header 값을 찾아낼 수 있다.")
    @Test
    void findHeader() {
        assertThat(headers.findHeader("Host")).isEqualTo("localhost:8080");
        assertThat(headers.findHeader("Connection")).isEqualTo("keep-alive");
        assertThat(headers.findHeader("Content-Length")).isEqualTo("59");
        assertThat(headers.findHeader("Content-Type")).isEqualTo("application/x-www-form-urlencoded");
        assertThat(headers.findHeader("Accept")).isEqualTo("*/*");
    }
}