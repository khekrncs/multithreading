/**
 * @author KK
 * @version 1.0
 */

package com.cs.multithreading.mutex;

import java.util.concurrent.atomic.AtomicInteger;

public class AtomicCounter {
    private final AtomicInteger count = new AtomicInteger(0);

    // This method is completely thread-safe without any locks!
    public void increment() {
        int newValue = count.incrementAndGet(); // Atomic increment
        if (newValue % 100 == 0) {
            System.out.println("Reached milestone: " + newValue);
        }
    }

    public void decrement() {
        int oldValue = count.getAndDecrement();
        System.out.println("Decremented from " + oldValue + " to " + (oldValue - 1));
    }

    public int getCount() {
        return count.get(); // Simple atomic read
    }

    public static void main(String[] args) throws InterruptedException {
        AtomicCounter counter = new AtomicCounter();
        Thread[] threads = new Thread[1000];
        for (int i = 0; i < 1000; i++) {
            threads[i] = new Thread(() -> {
                for (int j = 0; j < 1000; j++) {
                    counter.increment();
                }
            });
            threads[i].start();
        }
        // Wait for all threads to complete their work
        for (Thread thread : threads) {
            thread.join(); // This blocks until the thread finishes
        }

        System.out.println("Expected: 1000000, Actual: " + counter.getCount());
    }
}
