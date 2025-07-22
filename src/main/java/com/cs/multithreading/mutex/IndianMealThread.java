/**
 * @author KK
 * @version 1.0
 */

package com.cs.multithreading.mutex;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;

public class IndianMealThread {
    // Modern concurrency primitives - no synchronized needed!
    private static final AtomicBoolean dalSimmering = new AtomicBoolean(false);
    private static final AtomicBoolean riceCooking = new AtomicBoolean(false);
    private static final AtomicBoolean dalDone = new AtomicBoolean(false);

    // CountDownLatch for coordinated startup
    private static final CountDownLatch startSignal = new CountDownLatch(1);

    static class ConcurrentDal implements Runnable {
        private final String cookName;

        public ConcurrentDal(String cook) { this.cookName = cook; }

        @Override
        public void run() {
            try {
                // Wait for start signal
                startSignal.await();
                System.out.println("🥘 Time 1: " + cookName + " starts dal (put on stove)");
                Thread.sleep(2000); // Initial heating
                dalSimmering.set(true);
                System.out.println("🔥 Time 2: Dal is now simmering (chef can do other tasks)");
                Thread.sleep(6000); // Dal simmers while chef does other work
                dalDone.set(true);
                System.out.println("✅ Time 5: Dal is done! (Perfect timing)");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    static class ConcurrentRice implements Runnable {
        private final String cookName;

        public ConcurrentRice(String cook) { this.cookName = cook; }

        @Override
        public void run() {
            try {
                // Wait for dal to start simmering
                while (!dalSimmering.get()) {
                    Thread.sleep(100);
                }
                System.out.println("🍚 Time 3: Dal still cooking → " + cookName +
                        " starts rice (put on stove)");
                riceCooking.set(true);
                Thread.sleep(7000); // Rice takes longer
                System.out.println("✅ Time 7: Rice ready! Everything finished together! 🎉");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    static class ConcurrentSabji implements Runnable {
        private final String cookName;

        public ConcurrentSabji(String cook) { this.cookName = cook; }

        @Override
        public void run() {
            try {
                // Wait for dal to start simmering
                while (!dalSimmering.get()) {
                    Thread.sleep(100);
                }
                System.out.println("🥬 Time 2: While dal simmers → " + cookName +
                        " starts chopping vegetables for sabji");
                Thread.sleep(2000); // Chopping vegetables

                // Wait for dal to be done before starting sabji cooking
                while (!dalDone.get()) {
                    Thread.sleep(100);
                }
                System.out.println("🍳 Time 5: Dal done → start cooking sabji, rice still cooking");
                Thread.sleep(3000); // Cooking sabji
                System.out.println("✅ Time 6: Sabji finished while roti was being made!");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    static class ConcurrentRoti implements Runnable {
        private final String cookName;

        public ConcurrentRoti(String cook) { this.cookName = cook; }

        @Override
        public void run() {
            try {
                // Wait for both dal and rice to be cooking
                while (!dalSimmering.get() || !riceCooking.get()) {
                    Thread.sleep(100);
                }
                System.out.println("🫓 Time 4: Both dal & rice cooking → " + cookName +
                        " makes roti dough");
                Thread.sleep(2000); // Making dough
                System.out.println("🔄 Time 6: Rolling & cooking rotis while sabji cooks");
                Thread.sleep(2000); // Rolling and cooking rotis
                System.out.println("✅ Time 6: Rotis fresh and ready!");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public static void main(String[] args) {
        System.out.println("🏪 Chef Ravi's Restaurant - True Concurrent Meal Preparation");
        System.out.println("👨‍🍳 Smart chef managing multiple dishes with modern concurrency!\n");

        long startTime = System.currentTimeMillis();

        // Create realistic concurrent cooking tasks
        ConcurrentDal dalTask = new ConcurrentDal("Chef Ravi");
        ConcurrentSabji sabjiTask = new ConcurrentSabji("Chef Ravi");
        ConcurrentRoti rotiTask = new ConcurrentRoti("Chef Ravi");
        ConcurrentRice riceTask = new ConcurrentRice("Chef Ravi");

        // Start all dishes with intelligent coordination
        Thread dalThread = new Thread(dalTask, "DalCooking");
        Thread sabjiThread = new Thread(sabjiTask, "SabjiPrep");
        Thread rotiThread = new Thread(rotiTask, "RotiMaking");
        Thread riceThread = new Thread(riceTask, "RiceCooking");

        // Modern coordinated start - no synchronized block needed!
        System.out.println("🚀 Time 1: Starting coordinated meal preparation...\n");
        dalThread.start();     // Start dal first
        sabjiThread.start();   // Sabji waits for dal to simmer
        rotiThread.start();    // Roti waits for optimal time
        riceThread.start();    // Rice starts when dal is simmering

        // Signal all threads to begin their coordination
        startSignal.countDown();
        try {
            dalThread.join();
            sabjiThread.join();
            rotiThread.join();
            riceThread.join();

            long totalTime = (System.currentTimeMillis() - startTime) / 1000;
            System.out.println("\n🎉 MEAL COMPLETE! All dishes ready together! 🎉");
            System.out.printf("⏱️ Total preparation time: %d seconds\n", totalTime);
            System.out.println("🧠 Modern concurrency: Tasks coordinated with atomic operations!");

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("❌ Meal preparation interrupted!");
        }
    }
}
