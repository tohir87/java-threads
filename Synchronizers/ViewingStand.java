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
public class ViewingStand {
    
    private Semaphore seats;
    
    public ViewingStand(int seatCount){
        this.seats = new Semaphore(seatCount);
    }
    
    public void findSeat(int visitorID){
        try {
            System.out.println("Visitor: " + visitorID + " is looking for a seat");
            this.seats.acquire();
            System.out.println("Visitor: " + visitorID + " has gotten a seat");
        }catch(InterruptedException e){
            e.printStackTrace();
        }
    }
    
    public void leaveSeat(int visitorID){
            System.out.println("Visitor: " + visitorID + " is done with his/her seat");
            this.seats.release();
    }
    
}

class Visitor extends Thread{
    private ViewingStand v;
    private int visitorID;
    private static final Random random = new Random();
    
    public Visitor(ViewingStand v, int visitorID){
        this.v = v;
        this.visitorID = visitorID;
    }
    
    public void run() {
        v.findSeat(this.visitorID);
        try {
            System.out.println("Visitor: " + this.visitorID + " is done viewing.");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            v.leaveSeat(this.visitorID);
        }
    }
}
