package http;

import java.util.Arrays;

/**
 * HTTP 프로토콜 버전을 나타내는 클래스
 */
public class Version {
    /**
     * 프로토콜 버전을 나타내는 Enum
     */
    public enum ProtocolVersion {
        V_09("HTTP/0.9"),
        V_10("HTTP/1.0"),
        V_11("HTTP/1.1"),
        V_20("HTTP/2.0"),
        V_30("HTTP/3.0");

        private final String version;

        /**
         * ProtocolVersion Enum의 생성자입니다.
         *
         * @param version 프로토콜 버전 문자열
         */
        ProtocolVersion(String version) {
            this.version = version;
        }

        /**
         * 프로토콜 버전 문자열을 반환한다.
         *
         * @return 프로토콜 버전 문자열
         */
        public String getVersion() {
            return version;
        }
    }

    private ProtocolVersion version;

    /**
     * Version 클래스의 생성자
     *
     * @param version 프로토콜 버전 문자열
     */
    public Version(String version) {
        this.version = Arrays.stream(ProtocolVersion.values())
                .filter(protocolVersion -> protocolVersion.getVersion().equals(version))
                .findFirst()
                .orElseThrow(IllegalStateException::new);
    }

    /**
     * 현재 프로토콜 버전 문자열을 반환한다.
     *
     * @return 프로토콜 버전 문자열
     */
    public String getVersion() {
        return version.getVersion();
    }
}
