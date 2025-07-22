/**
 * @author KK
 * @version 1.0
 */

package com.cs.multithreading.mutex;

import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockCounter {
    private int count = 0;
    private final ReentrantLock lock = new ReentrantLock();

    public void increment() {
        lock.lock(); // Explicitly acquire the lock
        try {
            count++;
            if (count % 1000 == 0) {
                System.out.println("Milestone reached: " + count);
            }
        } finally {
            lock.unlock(); // Always release the lock, even if an exception occurs
        }
    }

    public void decrement() {
        lock.lock(); // Explicitly acquire the lock
        try {
            count--;
        } finally {
            lock.unlock(); // Always release the lock, even if an exception occurs
        }
    }

    public int getCount() {
        lock.lock(); // Explicitly acquire the lock
        try {
            return count;
        } finally {
            lock.unlock(); // Always release the lock, even if an exception occurs
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ReentrantLockCounter counter = new ReentrantLockCounter();
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
