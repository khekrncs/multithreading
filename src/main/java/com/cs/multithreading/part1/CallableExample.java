/**
 * @author KK
 * @version 1.0
 */

package com.cs.multithreading.part1;

import java.util.concurrent.*;

// Loan approval service that returns a result
class LoanApprovalService implements Callable<String> {
    private final String applicantName;
    private final double loanAmount;
    private final int creditScore;

    public LoanApprovalService(String applicantName, double loanAmount, int creditScore) {
        this.applicantName = applicantName;
        this.loanAmount = loanAmount;
        this.creditScore = creditScore;
    }

    @Override
    public String call() throws Exception {
        System.out.println("🔍 Evaluating loan application for: " + applicantName);

        // Simulate comprehensive loan evaluation
        Thread.sleep(1500); // Credit check, document verification, etc.

        // Loan approval logic
        boolean approved = false;
        String reason = "";

        if (creditScore >= 700 && loanAmount <= 200000) {
            approved = true;
            reason = "Excellent credit score and reasonable amount";
        } else if (creditScore >= 600 && loanAmount <= 100000) {
            approved = true;
            reason = "Good credit score with moderate amount";
        } else if (creditScore < 600) {
            reason = "Credit score too low";
        } else {
            reason = "Loan amount exceeds limit for credit score";
        }

        // Return detailed result
        return String.format("Applicant: %s | Amount: $%.2f | Credit Score: %d | Status: %s | Reason: %s",
                applicantName, loanAmount, creditScore, (approved ? "APPROVED" : "REJECTED"), reason);
    }
}

public class CallableExample {
    public static void main(String[] args) {
        System.out.println("Advanced Loan Approval System Started");

        // Create ExecutorService to manage threads
        ExecutorService executor = Executors.newFixedThreadPool(3);

        try {
            // Create loan applications
            LoanApprovalService app1 = new LoanApprovalService("Alice Miller", 85000, 750);
            LoanApprovalService app2 = new LoanApprovalService("Bob Wilson", 150000, 680);
            LoanApprovalService app3 = new LoanApprovalService("Carol Brown", 50000, 590);

            // Submit tasks and get Future objects
            Future<String> future1 = executor.submit(app1);
            Future<String> future2 = executor.submit(app2);
            Future<String> future3 = executor.submit(app3);

            // Get results (this will wait for each task to complete)
            System.out.println("📋 Loan Approval Results:");
            System.out.println(future1.get()); // Blocks until result is available
            System.out.println(future2.get());
            System.out.println(future3.get());

        } catch (InterruptedException | ExecutionException e) {
            System.out.println("Error processing loan applications: " + e.getMessage());
        } finally {
            executor.shutdown(); // Always shutdown the executor
        }
    }
}
