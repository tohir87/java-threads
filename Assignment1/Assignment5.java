package Assignment1;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Exchanger;

/**
 * Assignment5.java
 *
 * Student Name: Thohiru Omoloye 
 * Student Number: 2950574
 */
public class Assignment5 {

    public static void main(String[] args) {
        //Question 1
        //===============================================

        //Question 2
        //==============================================
        Thread p1 = new Thread(new Priority(1, 5));
        Thread p2 = new Thread(new Priority(6, 10));

        p1.setPriority(Thread.MAX_PRIORITY);
        p2.setPriority(Thread.MIN_PRIORITY);

        p1.start();
        p2.start();

        //===============================================
        //Question 3
        //==============================================
        Exchanger<ArrayList<String>> exchanger = new Exchanger<>();
        ExchangerProducer producer = new ExchangerProducer(exchanger);
        ExchangerConsumer consumer = new ExchangerConsumer(exchanger);
        producer.start();
        consumer.start();

        //===============================================
    }
}

//Q1 ===========================================================
final class Point {

    private final double x, y;

    public Point(double x0, double y0) {
        x = x0;
        y = y0;
    }

    public double x() {
        return x;
    }

    public double y() {
        return y;
    }

    public String toString() {
        return "(" + x + "," + y + ")";
    }

    public boolean equals(Object ob) {
        if (!(ob instanceof Point)) {
            return false;
        }
        Point p = (Point) ob;
        return x == p.x && y == p.y;
    }
}

class CollectionPoint {

    List<Point> list = new ArrayList<>();

    /**
     * Add element to arrayList
     *
     * @param p
     */
    public synchronized void add(Point p) {
        list.add(p);
    }

    /**
     * Search the array list
     *
     * @param p
     * @return boolean
     */
    public synchronized boolean search(Point p) {
        Boolean isFound = false;
        for (Point ab : list) {
            if (ab.equals(p)) {
                isFound = true;
                break;
            }
        }

        return isFound;
    }

    /**
     * return a list containing all values of x
     *
     * @param x
     * @return
     */
    public synchronized List<Point> getAllX(int x) {
        // declare an arrayList to store all x points
        List<Point> listx = new ArrayList<>();

        for (Point ab : list) {
            if (ab.x() == x) {
                listx.add(ab);
            }
        }

        return listx;
    }

    /**
     * This method will replace the p1 with p2 in the list
     *
     * @param p1
     * @param p2
     */
    public synchronized void replace(Point p1, Point p2) {
        list.set(list.indexOf(p1), p2);
    }

    /**
     * ToString implementation
     *
     * @return
     */
    public synchronized String toString() {
        String result = "";
        for (Point ab : list) {
            result += ab.toString();
        }
        return result;
    }

}
//End Q1 =======================================================

//Q2 ===========================================================
class Priority extends Thread {
    private int min;
    private int max;
    
    public Priority(int lb, int ub){
        this.min = lb;
        this.max = ub;
    }
    
    public void run(){
        for (int j = this.min; j <= this.max; j++) {
            System.out.println( j);
        }
        
    }
    
}
//End Q2 =======================================================


//Q3 ===========================================================
class ExchangerProducer extends Thread {

    private Exchanger<ArrayList<String>> exchanger;
    private ArrayList<String> buffer = new ArrayList<String>();

    public ExchangerProducer(Exchanger<ArrayList<String>> exchanger) {
        this.exchanger = exchanger;
    }

    public void run() {
        while (true) {
            try {
                System.out.println("producer");
                Thread.sleep(1000);
                fillBuffer();

                System.out.println("Producer has produced and waiting" + buffer);
                buffer = exchanger.exchange(buffer);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void fillBuffer() {
        // initialize a string array
        String[] list = {"one", "two", "three", "four", "five", "six"};
        for (int i = 0; i < list.length; i++) {
            buffer.add(list[i]);
        }
    }

}

class ExchangerConsumer extends Thread {

    private Exchanger<ArrayList<String>> exchanger;
    private ArrayList<String> buffer = new ArrayList<String>();

    public ExchangerConsumer(Exchanger<ArrayList<String>> exchanger) {
        this.exchanger = exchanger;
    }

    public void run() {
        while (true) {
            try {
                System.out.println("Consumer.");
                buffer = exchanger.exchange(buffer);

                System.out.println("Consumer has received" + buffer);
                Thread.sleep(1000);
                System.out.println("eating:" + buffer);
                buffer.clear();

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

//End Q3 =======================================================
