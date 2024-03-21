package http;

import java.util.Arrays;

public class Version {
    public enum ProtocolVersion {
        V_09("HTTP/0.9"),
        V_10("HTTP/1.0"),
        V_11("HTTP/1.1"),
        V_20("HTTP/2.0"),
        V_30("HTTP/3.0");

        private final String version;

        ProtocolVersion(String version) {
            this.version = version;
        }

        public String getVersion() {
            return version;
        }
    }

    private ProtocolVersion version;

    public Version(String version) {
        this.version = Arrays.stream(ProtocolVersion.values())
                .filter(protocolVersion -> protocolVersion.getVersion().equals(version))
                .findFirst()
                .orElseThrow(IllegalStateException::new);
    }

    public String getVersion() {
        return version.getVersion();
    }
}
