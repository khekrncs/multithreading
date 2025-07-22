/**
 * @author KK
 * @version 1.0
 */

package com.cs.multithreading.part1;

// UPI Payment processor implementing Runnable
class UPIPaymentProcessor implements Runnable {
    private final String fromAccount;
    private final String toAccount;
    private final double amount;
    private final String transactionId;

    public UPIPaymentProcessor(String fromAccount, String toAccount, double amount, String transactionId) {
        this.fromAccount = fromAccount;
        this.toAccount = toAccount;
        this.amount = amount;
        this.transactionId = transactionId;
    }

    @Override
    public void run() {
        System.out.println("🔄 Processing UPI payment: " + transactionId);

        try {
            // Simulate account verification
            Thread.sleep(500);
            System.out.println("Account verified for transaction: " + transactionId);

            // Simulate payment processing
            Thread.sleep(1000);
            System.out.println("Transferring $" + amount + " from " + fromAccount + " to " + toAccount);

            // Simulate final confirmation
            Thread.sleep(300);
            System.out.println("✅ Payment completed: " + transactionId);

        } catch (InterruptedException e) {
            System.out.println("❌ Payment interrupted: " + transactionId);
        }
    }
}

public class RunnableExample {
    public static void main(String[] args) {
        System.out.println("UPI Payment Processing System Started");

        // Create payment processors
        UPIPaymentProcessor payment1 = new UPIPaymentProcessor("alice@bank", "bob@bank", 150.0, "TXN001");
        UPIPaymentProcessor payment2 = new UPIPaymentProcessor("carol@bank", "dave@bank", 75.5, "TXN002");
        UPIPaymentProcessor payment3 = new UPIPaymentProcessor("eve@bank", "frank@bank", 300.0, "TXN003");

        // Create Thread objects and assign the Runnable tasks
        Thread thread1 = new Thread(payment1);
        Thread thread2 = new Thread(payment2);
        Thread thread3 = new Thread(payment3);

        // Start all payment processing threads
        thread1.start();
        thread2.start();
        thread3.start();

        System.out.println("All payments submitted for processing");
    }
}
