/**
 * @author KK
 * @version 1.0
 */

package com.cs.multithreading.demo;

public class VirtualThreadsDot {
    public static void main(String[] args) throws InterruptedException {
        //Thread[] threads = new Thread[1000000];
        for (int i = 0; i < 1000000; i++) {
           /* threads[i] = new Thread(() -> {
                System.out.print(".");
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
            threads[i].start();*/
            Thread.startVirtualThread( () -> {
                System.out.print(".");
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
        }

        Thread.sleep(150000);
    }
}
