/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Synchronizers;

import java.util.Random;
import java.util.concurrent.Semaphore;

/**
 *
 * @author 2950574
 */
public class Restaurant {
    private Semaphore tables;
    
    public Restaurant(int tablesCount){
        this.tables = new Semaphore(tablesCount);
    }
    
    public void getTable(int customerID){
        try {
                System.out.println("Customer: " + customerID + " is trying to access a table");
            tables.acquire();
            System.out.println("Customer: " + customerID + " has gotten a table");
        }catch(InterruptedException e){
        e.printStackTrace();
        }
    }
    
    public void returnTable(int customerID){
        System.out.println("Customer: " + customerID + " returned a table");
        tables.release();
    }
}
