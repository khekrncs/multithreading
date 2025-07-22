/**
 * @author KK
 * @version 1.0
 */

package com.cs.multithreading.runnables.dish;

public class Sabji implements Runnable {

    private final String chefName;

    public Sabji(String chefName) {
        this.chefName = chefName;
    }

    @Override
    public void run() {
        System.out.println("👨‍🍳 " + chefName + " started making sabji");
        try{
            Thread.sleep(6000);
        }catch(InterruptedException e){
            Thread.currentThread().interrupt();
            return;
        }
        System.out.println("✅ " + chefName + " finished sabji!");
    }
}
