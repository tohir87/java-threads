/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package __exchangerInt;

import java.util.ArrayList;
import java.util.concurrent.Exchanger;

/**
 *
 * @author tohir
 */
public class ExchangerProducer extends Thread{
    private Exchanger<ArrayList<Integer>> exchanger;
    private ArrayList<Integer> buffer = new ArrayList<Integer>();
    
    public ExchangerProducer(Exchanger<ArrayList<Integer>> exchanger){
        this.exchanger = exchanger;
    }
    
    public void run(){
        while (true){
            try{
                System.out.println("producer");
                Thread.sleep(1000);
                fillBuffer();
                
                System.out.println("Producer has produced and waiting" + buffer);
                buffer = exchanger.exchange(buffer);
            }catch(InterruptedException e){e.printStackTrace();}
        }
    }
    
    public void fillBuffer() {
        for (int i=0; i<=3; i++ ) {
            buffer.add(i);
        }
    }
    
}

class ExchangerConsumer extends Thread{
    private Exchanger<ArrayList<Integer>> exchanger;
    private ArrayList<Integer> buffer = new ArrayList<Integer>();
    public ExchangerConsumer(Exchanger<ArrayList<Integer>> exchanger) {
        this.exchanger = exchanger;
    }
    
    public void run(){
         while (true){
            try{
                System.out.println("Consumer.");
                buffer = exchanger.exchange(buffer);
                
                System.out.println("Consumer has received" + buffer);
                Thread.sleep(1000);
                System.out.println("eating:"+buffer);
                buffer.clear();
                
            }catch(InterruptedException e){e.printStackTrace();}
        }
    }
}


