/**
 * @author KK
 * @version 1.0
 */

package com.cs.multithreading.runnables.dish;

public class Roti implements Runnable {

    private final String chefName;

    public Roti(String chefName) {
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
