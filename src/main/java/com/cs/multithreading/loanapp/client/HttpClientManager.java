/**
 * @author KK
 * @version 1.0
 */

package com.cs.multithreading.loanapp.client;

import java.net.http.HttpClient;
import java.time.Duration;

public class HttpClientManager {

    private static final HttpClient HTTP_CLIENT = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(10))
            .build();

    public static HttpClient getHttpClient() {
        return HTTP_CLIENT;
    }
}
