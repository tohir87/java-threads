/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package runnable;

/**
 *
 * @author 2950574
 */
public class HelloRunnable implements Runnable{
    public void run(){
        System.out.println("Hi from java thread");
    }
    
    public static void main(String args[]){
        (new Thread(new HelloRunnable())).start();
        
        // Implementing runnable interface 1;
        Runnable p = new MessagePrinter("Happy days are here again", 7);
        Thread t = new Thread(p);
        t.start();
        
        // implementing runnable interface 2
        new Thread(new MessagePrinter("Second implementation of runnable", 5)).start();
    }
}
