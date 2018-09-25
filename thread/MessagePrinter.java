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
public class MessagePrinter extends Thread {
    
    private String message;
    private int n;

    MessagePrinter(String m, int p) {
        message = m;
        n = p;
    }
    
    public void run(){
        for (int k=0; k<n; k++) {
            System.out.println(message);
        }
    }
    
    
}
