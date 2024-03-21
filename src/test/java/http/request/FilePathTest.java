package http.request;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.io.FileNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class FilePathTest {
    @DisplayName("올바른 FilePath 요청이면 FilePath 객체를 생성할 수 있다.")
    @ValueSource(strings = {"/index.html", "/user/create", "/user/login", "/registration"})
    @ParameterizedTest
    void generateValidFilePath(String filePathUrl) throws FileNotFoundException {
        FilePath filePath = new FilePath(filePathUrl);
    }

    @DisplayName("알맞은 FilePath 요청이 아니면 FileNotFoundException을 던진다.")
    @ValueSource(strings = {"zzawang", "lucas", "java"})
    @ParameterizedTest
    void generateInvalidMethod(String filePathUrl) {
        assertThatThrownBy(() -> {
            FilePath filePath = new FilePath(filePathUrl);
        }).isInstanceOf(FileNotFoundException.class);
    }
}