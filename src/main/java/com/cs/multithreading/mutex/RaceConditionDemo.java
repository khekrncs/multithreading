/**
 * @author KK
 * @version 1.0
 */

package com.cs.multithreading.mutex;

public class RaceConditionDemo {
    private static int counter = 0;

    public static void main(String[] args) throws InterruptedException {
        // We'll create 1000 threads, each incrementing counter 1000 times
        // If everything works correctly, we should get 1,000,000
        Thread[] threads = new Thread[1000];

        for (int i = 0; i < 1000; i++) {
            threads[i] = new Thread(() -> {
                for (int j = 0; j < 1000; j++) {
                    counter++; // This is where the race condition happens!
                    // Multiple threads might read the same value, increment it,
                    // and write back the same result, losing increments
                }
            });
            threads[i].start();
        }

        // Wait for all threads to complete their work
        for (Thread thread : threads) {
            thread.join(); // This blocks until the thread finishes
        }

        System.out.println("Expected: 1000000, Actual: " + counter);
        // You'll likely see a number significantly less than 1,000,000!
        // Each run might produce a different result due to the non-deterministic
        // nature of thread scheduling
    }
}