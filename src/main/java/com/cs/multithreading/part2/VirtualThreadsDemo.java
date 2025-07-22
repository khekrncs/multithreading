/**
 * @author KK
 * @version 1.0
 */

package com.cs.multithreading.part2;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class VirtualThreadsDemo {

    public static void main(String[] args) {
        System.out.println("Java Virtual Threads Demo");
        System.out.println("Java Version: " + System.getProperty("java.version"));

        System.out.println("=== Basic Virtual Thread Creation ===");

        // Method 1: Using Thread.startVirtualThread()
        Thread virtualThread1 = Thread.startVirtualThread(() -> {
            System.out.println("Hello from virtual thread: " + Thread.currentThread());
            try {
                Thread.sleep(1000); // This will unmount the virtual thread
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            System.out.println("Virtual thread completed work");
        });

        // Method 2: Using Thread.ofVirtual()
        Thread virtualThread2 = Thread.ofVirtual()
                .name("my-virtual-thread")
                .start(() -> {
                    System.out.println("Named virtual thread: " + Thread.currentThread().getName());
                });

        // Method 3: Using Executor with virtual threads
        try (ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor()) {
            executor.submit(() -> {
                System.out.println("Virtual thread via executor: " + Thread.currentThread());
                return "Task completed";
            });
        } // Executor automatically closes and waits for completion

        // Wait for threads to complete
        try {
            virtualThread1.join();
            virtualThread2.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
