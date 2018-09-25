/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thread;

/**
 *
 * @author 2950574
 */
public class HelloThread extends Thread {
    
    public void run(){
        System.out.println("Hello from a thread");
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        (new HelloThread()).start();
        
        // call the message printer
         Thread p = new MessagePrinter("Happy days are here again", 10);
        p.start();
    }
    
}
