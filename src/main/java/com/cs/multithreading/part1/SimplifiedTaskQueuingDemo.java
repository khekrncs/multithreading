/**
 * @author KK
 * @version 1.0
 */

package com.cs.multithreading.part1;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

class SimplifiedExecutorExample {

    static class LoanApplicationTask implements Runnable {
        private final String applicationId;
        private final String applicantName;

        public LoanApplicationTask(String applicationId, String applicantName) {
            this.applicationId = applicationId;
            this.applicantName = applicantName;
        }

        @Override
        public void run() {
            System.out.printf("🔍 Thread %s: Starting loan application %s for %s%n",
                    Thread.currentThread().getName(), applicationId, applicantName);

            try {
                // Simulate consistent loan application processing time
                // This keeps the focus on thread pool behavior, not variable timing
                Thread.sleep(2000); // 2 seconds for each application

                System.out.printf("✅ Thread %s: Loan application %s completed for %s%n",
                        Thread.currentThread().getName(), applicationId, applicantName);

            } catch (InterruptedException e) {
                System.out.printf("❌ Thread %s: Loan application %s interrupted%n",
                        Thread.currentThread().getName(), applicationId);
                Thread.currentThread().interrupt();
            }
        }
    }

    public static void demonstrateTaskQueuing() {
        System.out.println("🏦 Demonstrating ExecutorService Task Management");
        System.out.println("Creating 6 loan application tasks with only 2 worker threads\n");

        // Create thread pool with only 2 threads
        ExecutorService loanProcessor = Executors.newFixedThreadPool(2);

        // Create 6 loan applications - notice how simple the constructor is now
        LoanApplicationTask[] applications = {
                new LoanApplicationTask("LOAN001", "Alice Johnson"),
                new LoanApplicationTask("LOAN002", "Bob Smith"),
                new LoanApplicationTask("LOAN003", "Carol Davis"),
                new LoanApplicationTask("LOAN004", "David Wilson"),
                new LoanApplicationTask("LOAN005", "Eva Brown"),
                new LoanApplicationTask("LOAN006", "Frank Miller")
        };

        System.out.println("📋 Submitting all 6 applications to the 2-thread pool:");

        // Submit all applications - watch the thread behavior clearly
        for (int i = 0; i < applications.length; i++) {
            loanProcessor.submit(applications[i]);
            System.out.printf("   Submitted application %d to executor%n", i + 1);
        }

        System.out.println("\n🔍 Observing thread pool behavior:");
        System.out.println("   • Applications 1 & 2 start immediately on the 2 available threads");
        System.out.println("   • Applications 3-6 wait in the queue");
        System.out.println("   • When a thread finishes, it immediately takes the next queued application");
        System.out.println("   • All work gets done with just 2 threads!\n");

        // Properly shutdown the executor
        loanProcessor.shutdown();

        try {
            // Wait for all tasks to complete (maximum 15 seconds should be plenty)
            if (!loanProcessor.awaitTermination(15, TimeUnit.SECONDS)) {
                System.out.println("⚠️ Some applications didn't complete in time");
                loanProcessor.shutdownNow();
            } else {
                System.out.println("\n🎉 All loan applications processed successfully!");
                System.out.println("💡 Key insight: 2 threads efficiently handled 6 applications through automatic queuing");
                System.out.println("💡 Each thread was reused multiple times instead of creating 6 separate threads");
            }
        } catch (InterruptedException e) {
            loanProcessor.shutdownNow();
        }
    }
}

public class SimplifiedTaskQueuingDemo {
    public static void main(String[] args) {
        SimplifiedExecutorExample.demonstrateTaskQueuing();
    }
}
