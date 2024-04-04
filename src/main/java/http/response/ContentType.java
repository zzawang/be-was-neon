package http.response;

/**
 * 파일의 콘텐츠 타입을 정의한 Enum
 */
public enum ContentType {
    txt("text/plain;charset=utf-8"),
    html("text/html;charset=utf-8"),
    css("text/css;charset=utf-8"),
    js("application/javascript;charset=utf-8"),
    ico("image/x-icon"),
    png("image/png"),
    gif("image/"),
    jpg("image/jpeg"),
    jpeg("image/jpeg"),
    svg("image/svg+xml");

    private final String contentType;


    /**
     * ContentType Enum의 생성자
     *
     * @param contentType 콘텐츠 타입 문자열
     */
    ContentType(String contentType) {
        this.contentType = contentType;
    }

    /**
     * 콘텐츠 타입을 반환한다.
     *
     * @return 콘텐츠 타입 문자열
     */
    public String getContentType() {
        return contentType;
    }
}
