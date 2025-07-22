/**
 * @author KK
 * @version 1.0
 */

package com.cs.multithreading.mutex;

import java.util.concurrent.CountDownLatch;

public class CountDownLatchExample {

    // This example simulates a application startup sequence where
    // several services must initialize before the application can start
    public void demonstrateApplicationStartup() throws InterruptedException {
        final int numberOfServices = 3;
        CountDownLatch startupLatch = new CountDownLatch(numberOfServices);

        // Start all services in parallel
        Thread databaseService = new Thread(() -> {
            try {
                System.out.println("Database service starting...");
                Thread.sleep(2000); // Simulate database initialization time
                System.out.println("Database service ready!");
                startupLatch.countDown(); // Signal that this service is ready
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        Thread webService = new Thread(() -> {
            try {
                System.out.println("Web service starting...");
                Thread.sleep(1500); // Simulate web service initialization
                System.out.println("Web service ready!");
                startupLatch.countDown(); // Signal that this service is ready
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        Thread cacheService = new Thread(() -> {
            try {
                System.out.println("Cache service starting...");
                Thread.sleep(1000); // Simulate cache initialization
                System.out.println("Cache service ready!");
                startupLatch.countDown(); // Signal that this service is ready
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        // Start all services
        databaseService.start();
        webService.start();
        cacheService.start();

        System.out.println("Main application waiting for all services to start...");

        // Wait for all services to complete initialization
        startupLatch.await(); // This blocks until count reaches zero

        System.out.println("All services are ready! Application startup complete.");
    }

    public static void main(String[] args) {
        CountDownLatchExample example = new CountDownLatchExample();
        try {
            example.demonstrateApplicationStartup();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
