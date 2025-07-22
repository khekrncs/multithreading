/**
 * @author KK
 * @version 1.0
 */

package com.cs.multithreading.part3;

class SafeBankAccount {
    private double balance = 1000.0;
    private String accountNumber;

    public SafeBankAccount(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    // SAFE: Only one thread can execute this method at a time
    public synchronized void deposit(double amount) {
        System.out.printf("Thread %s: Starting deposit of ₹%.2f to %s%n",
                Thread.currentThread().getName(), amount, accountNumber);

        double currentBalance = balance;

        // Even with processing delay, no other thread can interfere
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            return;
        }

        balance = currentBalance + amount;

        System.out.printf("Thread %s: Deposited ₹%.2f to %s, Balance: ₹%.2f%n",
                Thread.currentThread().getName(), amount, accountNumber, balance);
    }

    public synchronized double getBalance() {
        return balance;
    }

    public synchronized void withdraw(double amount) {
        System.out.printf("Thread %s: Attempting withdrawal of ₹%.2f from %s%n",
                Thread.currentThread().getName(), amount, accountNumber);

        if (balance >= amount) {
            double currentBalance = balance;

            try {
                Thread.sleep(100); // Simulate processing time
            } catch (InterruptedException e) {
                return;
            }

            balance = currentBalance - amount;
            System.out.printf("Thread %s: Withdrew ₹%.2f from %s, Balance: ₹%.2f%n",
                    Thread.currentThread().getName(), amount, accountNumber, balance);
        } else {
            System.out.printf("Thread %s: Insufficient funds for withdrawal of ₹%.2f%n",
                    Thread.currentThread().getName(), amount);
        }
    }
}

public class SynchronizedMethodDemo {

    public static void main(String[] args) throws InterruptedException {
        SafeBankAccount account = new SafeBankAccount("ACC456");
        System.out.printf("Initial balance: ₹%.2f%n%n", account.getBalance());

        // Create multiple threads for deposits and withdrawals
        Thread[] threads = new Thread[5];
        threads[0] = new Thread(() -> account.deposit(100.0), "Deposit-1");
        threads[1] = new Thread(() -> account.deposit(200.0), "Deposit-2");
        threads[2] = new Thread(() -> account.withdraw(50.0), "Withdraw-1");
        threads[3] = new Thread(() -> account.deposit(150.0), "Deposit-3");
        threads[4] = new Thread(() -> account.withdraw(75.0), "Withdraw-2");

        // Start all threads
        for (Thread thread : threads) {
            thread.start();
        }

        // Wait for all threads to complete
        for (Thread thread : threads) {
            thread.join();
        }

        System.out.printf("%nFinal balance: ₹%.2f%n", account.getBalance());
        System.out.printf("Expected balance: ₹%.2f%n", 1000.0 + 100.0 + 200.0 - 50.0 + 150.0 - 75.0);
        System.out.println("With synchronization, the result is always consistent!");
    }
}
