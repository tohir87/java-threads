/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package __mirror;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.*;
import java.util.concurrent.locks.*;

/**
 *
 * @author tohir
 */
public class ServerA {

    private final int port;
    private Ticket ticket;
    ServerSocket ss;
    ArrayList<Ticket> data;
    DataStore store;

    public ServerA(int p) throws IOException {
        this.port = p;

        init();

    }

    public ServerA(int p, Ticket t) {
        this.port = p;
        this.ticket = t;

        init();

    }

    void init() {
        data = new ArrayList<Ticket>();
        store = new DataStore(data);

        try {
            ss = new ServerSocket(this.port);
            System.out.println("Server A is listening on port: " + this.port);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void doTicketUpload(Ticket t) {
        new TicketUploader(store, this.ticket).start();
    }

    public void fetchTickets(String sellerCode) {
        System.out.println("size of data store" + data.size());
    }

}

class TicketUploader extends Thread{
    DataStore store;
    Ticket ticket;
    
    public TicketUploader(DataStore s, Ticket t) {
        this.store = s;
        this.ticket = t;
    }
    
    public void run(){
        store.add(this.ticket);
    }
}

class DataStore {
    private ArrayList<Ticket> data;
    
    public DataStore(ArrayList<Ticket> d) {
        data = d;
    }

//    private ArrayList<Ticket> data = new ArrayList<Ticket>();
    private Lock lock = new ReentrantLock();

    void add(Ticket t) {
        lock.lock();
        try {
            System.out.println("...about to add ticket");
            data.add(t);
            System.out.println("...ticke added");
            
        } finally {
            lock.unlock();
        }
    }

    boolean search(Ticket t) {
        lock.lock();
        try {
            return data.contains(t);
        } finally {
            lock.unlock();
        }
    }

    ArrayList<Ticket> retrieve(String sellerCode) {
        lock.lock();
        try {
            ArrayList<Ticket> dt = new ArrayList<Ticket>();
            Ticket t = new Ticket(sellerCode, ""); //use for search
            for (int j = 0; j < data.size(); j++) {
                Ticket t1 = data.get(j);
                if (t1.equals(t)) {
                    dt.add(t1);
                }
            }
            return dt;
        } finally {
            lock.unlock();
        }
    }

}
