/**
 * @author KK
 * @version 1.0
 */

package com.cs.multithreading.part1;

// LoanProcessor extends Thread to handle loan applications
class LoanProcessor extends Thread {
    private final String applicantName;
    private final double loanAmount;

    public LoanProcessor(String applicantName, double loanAmount) {
        this.applicantName = applicantName;
        this.loanAmount = loanAmount;
    }

    @Override
    public void run() {
        // This method contains the work that the thread will perform
        System.out.println("Processing loan for: " + applicantName);

        // Simulate loan processing time (checking credit score, documents, etc.)
        try {
            Thread.sleep(2000); // Represents 2 seconds of processing time
        } catch (InterruptedException e) {
            System.out.println("Loan processing interrupted for: " + applicantName);
            return;
        }

        // Simple loan approval logic
        if (loanAmount <= 100000) {
            System.out.println("✅ Loan approved for " + applicantName + " - Amount: $" + loanAmount);
        } else {
            System.out.println("❌ Loan rejected for " + applicantName + " - Amount too high: $" + loanAmount);
        }
    }
}

// Example usage
public class ThreadExtensionExample {
    public static void main(String[] args) {
        System.out.println("Bank Loan Processing System Started");

        // Create multiple loan processor threads
        LoanProcessor loan1 = new LoanProcessor("Nirmal", 75000);
        LoanProcessor loan2 = new LoanProcessor("Vijay", 120000);
        LoanProcessor loan3 = new LoanProcessor("Sam", 45000);

        // Start all threads - they will run concurrently
        loan1.start(); // Don't call run() directly - always use start()
        loan2.start();
        loan3.start();

        System.out.println("All loan applications submitted for processing");
    }
}
