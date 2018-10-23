/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Synchronizers;

import java.util.Random;

/**
 *
 * @author 2950574
 */
public class RestuarantCustomer extends Thread{
    private Restaurant r;
    private int customerID;
    private static final Random random = new Random();
    
    public RestuarantCustomer(Restaurant r, int customerID){
        this.r = r;
        this.customerID = customerID;
    }
    
    public void run() {
        r.getTable(this.customerID);
        try {
            int eatingTime = random.nextInt(30) + 1;
            System.out.println("Customer: " + this.customerID + " will eat for " + eatingTime + " seconds.");
            Thread.sleep(eatingTime * 1000);
            System.out.println("Customer: " + this.customerID + " is done eating.");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            r.returnTable(this.customerID);
        }
    }
}
