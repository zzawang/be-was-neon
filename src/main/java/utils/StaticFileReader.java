package utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * 정적 파일을 읽는 유틸리티 클래스
 */
public class StaticFileReader {
    private File file;

    /**
     * StaticFileReader의 생성자
     * @param file 읽을 파일
     */
    public StaticFileReader(File file) {
        this.file = file;
    }

    /**
     * 파일의 모든 바이트를 읽는다.
     * @return 파일의 모든 바이트 배열
     * @throws RuntimeException 파일 읽기 중 발생한 예외
     */
    public byte[] readAllBytes() {
        try (FileInputStream inputStream = new FileInputStream(file)) {
            byte[] buffer = new byte[(int) file.length()]; // 파일 길이만큼 바이트 배열 생성
            int bytesRead = inputStream.read(buffer); // 파일 내용 읽기

            if (bytesRead != file.length()) { // 잘못 변환된 경우 예외 처리
                throw new IOException();
            }
            return buffer;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
