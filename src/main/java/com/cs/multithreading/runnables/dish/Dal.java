/**
 * @author KK
 * @version 1.0
 */

package com.cs.multithreading.runnables.dish;

public class Dal implements Runnable {

    private final String chefName;

    public Dal(String chefName) {
        this.chefName = chefName;
    }

    @Override
    public void run() {
        System.out.println("👨‍🍳 " + chefName + " started making dal");
        try{
            Thread.sleep(10000);
        }catch(InterruptedException e){
            Thread.currentThread().interrupt();
            return;
        }
        System.out.println("✅ " + chefName + " finished dal!");
    }
}
