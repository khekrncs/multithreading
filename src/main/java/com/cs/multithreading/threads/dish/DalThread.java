/**
 * @author KK
 * @version 1.0
 */

package com.cs.multithreading.threads.dish;

public class DalThread extends Thread {
    private final String chefName;

    public DalThread(String chefName) {
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
