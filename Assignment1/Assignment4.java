

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.concurrent.Semaphore;

/**
 *
 * @Student Name: Thohiru Omoloye
 * @Student Number: 2950574
 *
 * @author tohir
 */
public class Assignment4 {

    public static void main(String args[]) {
        ViewingStand picasso = new ViewingStand(2);
        for (int i = 1; i <= 10; i++) {
            Visitor c = new Visitor(picasso, i);
            c.start();
        }
    }
}

class ViewingStand {

    private Semaphore seats;

    public ViewingStand(int seatCount) {
        this.seats = new Semaphore(seatCount);
    }

    public void findSeat(int visitorID) {
        try {
            System.out.println("Visitor: " + visitorID + " is looking for a seat");
            this.seats.acquire();
            System.out.println("Visitor: " + visitorID + " has gotten a seat");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void leaveSeat(int visitorID) {
        System.out.println("Visitor: " + visitorID + " is done with his/her seat");
        this.seats.release();
    }

}

class Visitor extends Thread {

    private ViewingStand v;
    private int visitorID;
    private static final Random random = new Random();

    public Visitor(ViewingStand v, int visitorID) {
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

class CircularQueue<T> implements Iterable<T> {

    private T queue[];
    private int head, tail, size;

    @SuppressWarnings("unchecked")
    public CircularQueue() {
        queue = (T[]) new Object[20];
        head = 0;
        tail = 0;
        size = 0;
    }

    @SuppressWarnings("unchecked")
    public CircularQueue(int n) { //assume n >=0
        queue = (T[]) new Object[n];
        size = 0;
        head = 0;
        tail = 0;
    }

    public synchronized boolean join(T x) {
        if (size < queue.length) {
            queue[tail] = x;
            tail = (tail + 1) % queue.length;
            size++;
            return true;
        } else {
            return false;
        }
    }

    public synchronized T top() {
        if (size > 0) {
            return queue[head];
        } else {
            return null;
        }
    }

    public synchronized boolean leave() {
        if (size == 0) {
            return false;
        } else {
            head = (head + 1) % queue.length;
            size--;
            return true;
        }
    }

    public synchronized boolean full() {
        return (size == queue.length);
    }

    public synchronized boolean empty() {
        return (size == 0);
    }

    public Iterator<T> iterator() {
        return new QIterator<T>(queue, head, size);
    }

    private static class QIterator<T> implements Iterator<T> {

        private T[] d;
        private int index;
        private int size;
        private int returned = 0;

        QIterator(T[] dd, int head, int s) {
            d = dd;
            index = head;
            size = s;
        }

        public boolean hasNext() {
            return returned < size;
        }

        public T next() {
            if (returned == size) {
                throw new NoSuchElementException();
            }
            T item = (T) d[index];
            index = (index + 1) % d.length;
            returned++;
            return item;
        }

        public void remove() {
        }
    }
}
