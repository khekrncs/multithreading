/**
 * @author KK
 * @version 1.0
 */

package com.cs.multithreading.runnables.dish;

public class Rice implements Runnable {

    private final String chefName;

    public Rice(String chefName) {
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