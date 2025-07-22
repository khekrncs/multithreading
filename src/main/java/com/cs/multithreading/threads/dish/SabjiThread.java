/**
 * @author KK
 * @version 1.0
 */

package com.cs.multithreading.threads.dish;

public class SabjiThread extends Thread {

    private final String chefName;

    public SabjiThread(String chefName) {
        this.chefName = chefName;
    }

    @Override
    public void run() {
        System.out.println("👨‍🍳 " + chefName + " started making sabji");
        try {
            Thread.sleep(6000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return;
        }
        System.out.println("✅ " + chefName + " finished sabji!");
    }
}
