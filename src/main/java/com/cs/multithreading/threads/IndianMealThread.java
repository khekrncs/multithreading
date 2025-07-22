/**
 * @author KK
 * @version 1.0
 */

package com.cs.multithreading.threads;

import com.cs.multithreading.threads.dish.DalThread;
import com.cs.multithreading.threads.dish.RiceThread;
import com.cs.multithreading.threads.dish.RotiThread;
import com.cs.multithreading.threads.dish.SabjiThread;

public class IndianMealThread {

    public static void main(String[] args) throws Exception {
        String chefName = "Chef Ravi";

        DalThread dalTask = new DalThread(chefName);
        dalTask.start();

        SabjiThread sabjiTask = new SabjiThread(chefName);
        sabjiTask.start();

        RiceThread riceTask = new RiceThread(chefName);
        riceTask.start();

        RotiThread rotiTask = new RotiThread(chefName);
        rotiTask.start();

        System.out.println("Indian Meal is ready !! ");
    }
}
