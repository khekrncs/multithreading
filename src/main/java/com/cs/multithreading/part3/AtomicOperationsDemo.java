/**
 * @author KK
 * @version 1.0
 */

package com.cs.multithreading.part3;

import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

class AtomicTransactionCounter {
    private final AtomicLong totalTransactions = new AtomicLong(0);
    private final AtomicLong totalAmount = new AtomicLong(0);
    private final AtomicReference<String> lastTransactionId =
            new AtomicReference<>("NONE");

    public void recordTransaction(String transactionId, long amountInCents) {
        // These operations are atomic - no synchronization needed
        totalTransactions.incrementAndGet();
        totalAmount.addAndGet(amountInCents);
        lastTransactionId.set(transactionId);

        System.out.printf("Thread %s: Recorded transaction %s for ₹%.2f%n",
                Thread.currentThread().getName(), transactionId, amountInCents / 100.0);
    }
    public long getTotalTransactions() {
        return totalTransactions.get();
    }

    public double getTotalAmount() {
        return totalAmount.get() / 100.0; // Convert cents to rupees
    }

    public String getLastTransactionId() {
        return lastTransactionId.get();
    }

    // Atomic compare-and-swap operation
    public boolean updateLastTransactionIfNewer(String newTransactionId,
                                                String expectedCurrentId) {
        return lastTransactionId.compareAndSet(expectedCurrentId, newTransactionId);
    }
}

public class AtomicOperationsDemo {
    public static void main(String[] args) throws InterruptedException {
        AtomicTransactionCounter counter = new AtomicTransactionCounter();

        // Create multiple threads to record transactions simultaneously
        Thread[] recorders = new Thread[10];

        for (int i = 0; i < 10; i++) {
            final int threadNum = i;
            recorders[i] = new Thread(() -> {
                for (int j = 1; j <= 5; j++) {
                    String transactionId = String.format("TXN-%d-%d", threadNum, j);
                    long amount = (long)((Math.random() * 10000) + 1000); // ₹10 to ₹100
                    counter.recordTransaction(transactionId, amount);

                    try {
                        Thread.sleep(10); // Small delay between transactions
                    } catch (InterruptedException e) {
                        break;
                    }
                }
            }, "Recorder-" + i);
        }
        for (Thread recorder : recorders) {
            recorder.start();
        }

        // Wait for all threads to complete
        for (Thread recorder : recorders) {
            recorder.join();
        }

        System.out.printf("%nFinal Results:%n");
        System.out.printf("Total Transactions: %d%n", counter.getTotalTransactions());
        System.out.printf("Total Amount: ₹%.2f%n", counter.getTotalAmount());
        System.out.printf("Last Transaction ID: %s%n", counter.getLastTransactionId());
        System.out.println("All operations were thread-safe using atomic classes!");
    }
}
