/**
 * @author KK
 * @version 1.0
 */

package com.cs.multithreading.runnables;


import com.cs.multithreading.runnables.dish.Dal;
import com.cs.multithreading.runnables.dish.Rice;
import com.cs.multithreading.runnables.dish.Roti;
import com.cs.multithreading.runnables.dish.Sabji;


public class IndianMealRunnable {

    public static void main(String[] args) throws Exception {
        String chefName = "Chef Ravi";

        Dal dalTask = new Dal(chefName);
        Sabji sabjiTask = new Sabji(chefName);
        Rice riceTask = new Rice(chefName);
        Roti rotiTask = new Roti(chefName);

        Thread dalThread = new Thread(dalTask);
        dalThread.start();

        Thread sabjiThread = new Thread(sabjiTask);
        sabjiThread.start();

        Thread riceThread = new Thread(riceTask);
        riceThread.start();

        Thread rotiThread = new Thread(rotiTask);
        rotiThread.start();

        dalThread.join();
        sabjiThread.join();
        riceThread.join();
        rotiThread.join();

        System.out.println("Indian Meal is ready !! ");
    }
}
