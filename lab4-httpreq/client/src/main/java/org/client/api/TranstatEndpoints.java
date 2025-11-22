package org.client.api;

import org.client.ClientConfig;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public final class TranstatEndpoints {

    // Wskaźnik 1-1-7, C007MInd117p – liczba wejść/wyjść statków do/z portu (dzienna)
    private static final String BASE_PATH =
            ClientConfig.BASE_URL + "/api/v1/C007MInd117p";

    private TranstatEndpoints() {
    }

    public static String base() {
        return BASE_PATH;
    }

    public static String forMapParam(String mapParam) {
        return BASE_PATH + "/MapParam/" + encodeSegment(mapParam);
    }

    public static String forMultiParamPl(String multiParamPl) {
        return BASE_PATH + "/MultiParamPl/" + encodeSegment(multiParamPl);
    }

    private static String encodeSegment(String value) {
        return URLEncoder.encode(value, StandardCharsets.UTF_8);
    }
}
