package semaphores;

import java.util.concurrent.Semaphore;

/**
 *
 * @author tohir
 */
public class Semaphores {

    public static void main(String args[]) {
        Semaphore sem = new Semaphore(1);
        
        
            
            try {
                for (int i = 1; i < 9; i++) {
                Visit c = new Visit(i, i * 100, sem);
                c.start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
}


class Visit extends Thread {
    private int num;
    private int sleepTime;
    private Semaphore s;
    
    public Visit(int n, int t, Semaphore s){
        this.num = n;
        this.sleepTime = t;
        this.s = s;
        
    }
    
    public void run() {
        
        try{
             this.s.acquire();
            Thread.sleep(this.sleepTime);
            
        }catch(InterruptedException e){
            e.printStackTrace();
        }finally{
            System.out.println("My sequence number is :" + this.num);
            this.s.release();
        }
    }
}