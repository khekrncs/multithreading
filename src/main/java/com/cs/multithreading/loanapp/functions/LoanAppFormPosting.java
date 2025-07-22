/**
 * @author KK
 * @version 1.0
 */

package com.cs.multithreading.loanapp.functions;

import com.cs.multithreading.loanapp.client.HttpClientManager;
import com.cs.multithreading.loanapp.model.LoanInput;
import com.cs.multithreading.loanapp.model.LoanOutput;
import com.cs.multithreading.loanapp.utils.JsonUtils;

import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.Callable;

public class LoanAppFormPosting implements Callable<LoanOutput> {

    private static final String LOAN_APP_FORM_URL = "http://localhost:8080/application";

    private final LoanInput loanInput;

    public LoanAppFormPosting(LoanInput loanInput) {
        this.loanInput = loanInput;
    }

    @Override
    public LoanOutput call() throws Exception {
        System.out.printf("🚀 Thread %s: Posting loan application for %s%n",
                Thread.currentThread().getName(), loanInput.name());

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(LOAN_APP_FORM_URL))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(JsonUtils.toJson(loanInput)))
                .build();

        HttpResponse<String> response = HttpClientManager.getHttpClient().send(request,
                HttpResponse.BodyHandlers.ofString());
        

        System.out.printf("✅ Thread %s: Loan posting completed with status: %d%n",
                Thread.currentThread().getName(), response.statusCode());
        
        // Convert JSON response to LoanOutput using JsonUtils
        return JsonUtils.fromJson(response.body(), LoanOutput.class);
    }
}
