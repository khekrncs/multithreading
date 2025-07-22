/**
 * @author KK
 * @version 1.0
 */

package com.cs.multithreading.threads.dish;

public class RotiThread extends Thread {
    private final String chefName;

    public RotiThread(String chefName) {
        this.chefName = chefName;
    }

    @Override
    public void run() {
        System.out.println("👨‍🍳 " + chefName + " started making roti");
        try{
            Thread.sleep(7000);
        }catch(InterruptedException e){
            Thread.currentThread().interrupt();
            return;
        }
        System.out.println("✅ " + chefName + " finished roti!");
    }
}
