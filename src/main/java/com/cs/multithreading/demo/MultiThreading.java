/**
 * @author KK
 * @version 1.0
 */

package com.cs.multithreading.demo;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

class LoanProcessor extends Thread{
    private final String applicantName;
    private final Double amount;

    LoanProcessor(String applicantName, Double amount) {
        this.applicantName = applicantName;
        this.amount = amount;
    }

    public void run(){
        System.out.println("Loan Application for " + applicantName);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Loan Approved for " + applicantName + " - Amount: " + amount);
    }
}

class UPIPayment implements Runnable{

    private final String fromAccount;
    private final String toAccount;
    private final Double amount;
    private final String transactionId;

    UPIPayment(String fromAccount, String toAccount, Double amount, String transactionId) {
        this.fromAccount = fromAccount;
        this.toAccount = toAccount;
        this.amount = amount;
        this.transactionId = transactionId;
    }

    @Override
    public void run() {
        System.out.println("Processing UPI payment: " + transactionId);

        try {
            System.out.println("Validating account: " + fromAccount + " for transaction: " + transactionId);
            Thread.sleep(5000);

            System.out.println("Transferring $" + amount + " from " + fromAccount + " to " + toAccount);
            Thread.sleep(10000);

            System.out.println("Payment completed: " + transactionId);
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Account verified for transaction: " + transactionId);
    }
}

public class MultiThreading {
    public static void main(String[] args) throws InterruptedException {
//        LoanProcessor nirmal = new LoanProcessor("Nirmal", 10000D);
//        LoanProcessor vijay = new LoanProcessor("Vijay", 20000D);
//        LoanProcessor sam = new LoanProcessor("Sam", 30000D);
//
//        nirmal.start();
//        vijay.start();
//        sam.start();
//
//        try{
//            nirmal.join();
//            vijay.join();
//            sam.join();
//        }catch (Exception e){
//            System.out.println("Error : "+e);
//        }
//
//
//        System.out.println("Loan applications processed successfully for all applicants !");


//        Thread vijayThread = new Thread(vijayPayment);
//        Thread samThread = new Thread(samPayment);
//        Thread nirmalThread = new Thread(nirmalPayment);
//
//        vijayThread.start();
//        samThread.start();
//        nirmalThread.start();
//
//        try{
//            vijayThread.join();
//            samThread.join();
//            nirmalThread.join();
//        }catch (Exception e){
//            System.out.println("Error : "+e);
//        }
//        System.out.println("All payments processed successfully !");

        UPIPayment vijayPayment = new UPIPayment("abc@bank", "xyz@bank", 10000D, "TXN001");
        UPIPayment samPayment = new UPIPayment("abc@bank", "xyz@bank", 20000D, "TXN002");
        UPIPayment nirmalPayment = new UPIPayment("abc@bank", "xyz@bank", 30000D, "TXN003");

        ExecutorService executorService = Executors.newFixedThreadPool(2);
        executorService.submit(vijayPayment);
        executorService.submit(samPayment);
        executorService.submit(nirmalPayment);

        //System.out.println("All payments processed successfully !");
        executorService.shutdown();

        if (executorService.awaitTermination(2, TimeUnit.SECONDS)){
            System.out.println("All payments not processed successfully !");
            executorService.shutdownNow();
        }


    }
}
