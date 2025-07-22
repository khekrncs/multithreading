/**
 * @author KK
 * @version 1.0
 */

package com.cs.multithreading.loanapp.functions;

import com.cs.multithreading.loanapp.client.HttpClientManager;
import com.cs.multithreading.loanapp.model.UnderwritingStatus;
import com.cs.multithreading.loanapp.utils.JsonUtils;

import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.Callable;

public class UnderwritingCallable implements Callable<UnderwritingStatus> {

    private static final String UNDERWRITING_URL = "http://localhost:8080/underwriting";

    private final String applicationId;

    public UnderwritingCallable(String applicationId) {
        this.applicationId = applicationId;
    }

    @Override
    public UnderwritingStatus call() throws Exception {
        System.out.printf("🔍 Thread %s: Starting underwriting for application %s%n",
                Thread.currentThread().getName(), applicationId);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(UNDERWRITING_URL + "?id=" + applicationId))
                .GET()
                .build();

        HttpResponse<String> response = HttpClientManager.getHttpClient().send(request,
                HttpResponse.BodyHandlers.ofString());

        System.out.printf("📊 Thread %s: Underwriting completed for %s with status: %d%n",
                Thread.currentThread().getName(), applicationId, response.statusCode());

        if (response.statusCode() == 404) {
            throw new RuntimeException("Application not found: " + applicationId);
        }

        // Convert JSON response to UnderwritingStatus using JsonUtils
        return JsonUtils.fromJson(response.body(), UnderwritingStatus.class);
    }
}
