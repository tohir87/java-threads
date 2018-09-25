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
public class MessagePrinter implements Runnable{
    private final String message;
    private final int n;

    MessagePrinter(String m, int p) {
        message = m;
        n = p;
    }
    
    public void run(){
        int k = 0;
        while(k < n){
            System.out.println(message);
            k++;
        }
    }
    
    
}
