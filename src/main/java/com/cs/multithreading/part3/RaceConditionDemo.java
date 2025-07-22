/**
 * @author KK
 * @version 1.0
 */

package com.cs.multithreading.part3;

class UnsafeBankAccount {
    private double balance = 1000.0; // Starting balance
    private final String accountNumber;

    public UnsafeBankAccount(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public void deposit(double amount) {
        System.out.printf("Thread %s: Starting deposit of ₹%.2f to %s%n",
                Thread.currentThread().getName(), amount, accountNumber);

        // This is where the race condition happens
        double currentBalance = balance;     // Read current balance

        // Simulate some processing time (network delay, validation, etc.)
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            return;
        }

        double newBalance = currentBalance + amount;  // Calculate new balance
        balance = newBalance;                         // Write back new balance

        System.out.printf("Thread %s: Deposited ₹%.2f to %s, Balance: ₹%.2f%n",
                Thread.currentThread().getName(), amount, accountNumber, balance);
    }

    public double getBalance() {
        return balance;
    }
}

public class RaceConditionDemo {
    public static void main(String[] args) throws InterruptedException {
        UnsafeBankAccount account = new UnsafeBankAccount("ACC123");
        System.out.printf("Initial balance: ₹%.2f%n%n", account.getBalance());

        // Create multiple threads trying to deposit simultaneously
        Thread depositor1 = new Thread(() -> account.deposit(100.0), "Depositor-1");
        Thread depositor2 = new Thread(() -> account.deposit(200.0), "Depositor-2");
        Thread depositor3 = new Thread(() -> account.deposit(150.0), "Depositor-3");

        // Start all threads at roughly the same time
        depositor1.start();
        depositor2.start();
        depositor3.start();

        // Wait for all threads to complete
        depositor1.join();
        depositor2.join();
        depositor3.join();

        System.out.printf("%nFinal balance: ₹%.2f%n", account.getBalance());
        System.out.printf("Expected balance: ₹%.2f%n", 1000.0 + 100.0 + 200.0 + 150.0);
        System.out.println("Notice how the final balance is often wrong due to race condition!");
    }
}

