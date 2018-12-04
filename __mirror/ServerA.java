/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package __mirror;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.*;
import java.util.concurrent.locks.*;
import java.util.*;

/**
 *
 * @author tohir
 */
public class ServerA extends Thread {

    private final int port;
    private Ticket ticket;
    ServerSocket ss;
    ArrayList<Ticket> data;
    DataStore store;

    public ServerA(int p) throws IOException {
        this.port = p;

    }

    public void run() {
        data = new ArrayList<Ticket>();
        store = new DataStore(data);

        try {
            ss = new ServerSocket(this.port);
            System.out.println("Server A is listening on port: " + this.port);
            while (true) {
                Socket sock = ss.accept();
                System.out.println("connected to a client...");

                DataInputStream input = new DataInputStream(sock.getInputStream());
//            Ticket tic = (Ticket) objectInputStream.readObject();
                String seller = input.readUTF();
                String t_num = input.readUTF();
                Ticket tic = new Ticket(seller, t_num);
                System.out.println("recieved data from client:" + seller + t_num);

                try {
                    doTicketUpload(tic);

                    Thread.sleep(200);
                    fetchTickets(seller);
                } catch (InterruptedException e) {

                }
            }

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

class TicketUploader extends Thread {

    DataStore store;
    Ticket ticket;
    Buffer buffer;

    public TicketUploader(DataStore s, Ticket t) {
        this.store = s;
        this.ticket = t;
        this.buffer = new Buffer();
    }

    public void run() {
        // send a copy of ticket to data store
        this.store.add(this.ticket);
        // send a copy of ticket to buffer
        this.buffer.put(this.ticket);
    }
}

class DataStore {

    private ArrayList<Ticket> data;

    public DataStore(ArrayList<Ticket> d) {
        data = d;
    }

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

class Buffer{
   private int max;
   private int size = 1000;
   private ArrayList<Ticket> buffer;
   private Semaphore empty;  // control consumer
   private Semaphore full;   // control producer
   private Lock lock = new ReentrantLock();
   private WriterReaderServer WRServer;
   private DataStore store;
   
   private final int READER_PORT = 2000;
   private final int WRITER_PORT = 2001;
   
   public Buffer(){
   	 buffer = new ArrayList<Ticket>();
   	 empty = new Semaphore(0);
   	 full = new Semaphore(size);
     
   }

   public void put(Ticket x){
   	 try{
   		full.acquire();
   	 }
   	 catch(InterruptedException e){}
   	 // synchronize update of buffer
   	 lock.lock();
   	 try{
   	 	buffer.add(x);
        
   	    size++;
   	    empty.release();
   	 }finally{
         new WriterReaderServer(READER_PORT, WRITER_PORT, x, store);
   	 	lock.unlock();
   	 }
   }
   
   public Ticket get(){
   		try{
   			empty.acquire();
   		}
   		catch(InterruptedException e){}
   		// synchronize update of buffer
        lock.lock();
        try{
        	Ticket temp = buffer.get(0);
   	      buffer.remove(0);
   	      size--;
   	      full.release();
   	      return temp;
        }finally{
        	lock.unlock();
        }
   }

}

class Writer{
    
}
