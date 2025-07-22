/**
 * @author KK
 * @version 1.0
 */

package com.cs.multithreading.mutex;

public class SynchronizedCounter {
    private int count = 0;

    // The 'synchronized' keyword here means that only one thread
    // can execute this method at a time for any given instance
    public synchronized void increment() {
        count++; // Now this operation is atomic from the outside perspective
        // Even though count++ still involves read-modify-write internally,
        // the synchronized method ensures no other thread can interfere
    }

    // It's important to synchronize read operations too!
    // Without synchronization, a reading thread might see a partially
    // updated value or a stale cached value
    public synchronized int getCount() {
        return count;
    }

    // You can also synchronize other operations on the same data
    public synchronized void decrement() {
        count--;
    }

    // This method shows how synchronized methods can safely call each other
    public synchronized void reset() {
        count = 0;
        // Since this thread already holds the lock, it can call other
        // synchronized methods without deadlocking (this is called "reentrant")
        System.out.println("Counter reset. Current value: " + getCount());
    }

    public static void main(String[] args) throws Exception {
        SynchronizedCounter counter = new SynchronizedCounter();
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
