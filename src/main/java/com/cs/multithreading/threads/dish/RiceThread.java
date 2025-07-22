/**
 * @author KK
 * @version 1.0
 */

package com.cs.multithreading.threads.dish;

public class RiceThread extends Thread {
    private final String chefName;

    public RiceThread(String chefName) {
        this.chefName = chefName;
    }

    @Override
    public void run() {
        System.out.println("👨‍🍳 " + chefName + " started making rice");
        try{
            Thread.sleep(8000);
        }catch(InterruptedException e){
            Thread.currentThread().interrupt();
            return;
        }
        System.out.println("✅ " + chefName + " finished rice!");
    }
}
