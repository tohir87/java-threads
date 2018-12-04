
package __mirror;

import java.io.*;
import java.net.*;
import java.util.concurrent.*;
import java.util.*;
import sun.misc.IOUtils;

public class WriterReaderServer {
    private int RPort;
    private int WPort;
    private Ticket buffer;
    private DataStore store;
    
    public WriterReaderServer(int rPort, int wPort, Ticket b, DataStore st){
        this.RPort = rPort;
        this.WPort = wPort;
        this.buffer = b;
        this.store = st;
        
        init();
    }
    
    void init(){
        new ReaderServer(this.RPort, this.store).start();
        new WriterServer(this.WPort, this.buffer.getSellerCode(), this.buffer.getTicketNumber()).start();
//        new WriterServer(this.buffer, this.WPort).start();
    }
    
    
    
 
}
class ReaderServer extends Thread{
//	private ArrayList<Ticket> buffer;
    private Buffer buf;
	private int port;
    DataStore store;
    
	ReaderServer(int pt, DataStore st){
        this.port = pt;
        this.store = st;
    }
    
	public void run(){
	   System.out.println("Reader Server running ... ");
		 try {
        ServerSocket readsock = new ServerSocket(port);
        while (true) {
          Socket socket = readsock.accept();
          readData(socket);
        }
     } catch (IOException e) {e.printStackTrace();}
	}
    
    private void readData(Socket sk) {
        try {
            DataInputStream in = new DataInputStream(sk.getInputStream());

            String sellerCode = in.readUTF();
            String ticketNum = in.readUTF();

            try {
                this.store.add(new Ticket(sellerCode, ticketNum));
                
            } catch (Exception e) {
                e.printStackTrace();
            }
            in.close();
        } catch (IOException e) {
        }

    }
}
class WriterServer extends Thread{
	private ArrayBlockingQueue<Integer> buffer;
	private int port;
    String seller_code;
    String ticket_num;
    
    
	WriterServer(int pt, String s_code, String t_num){ 
        port = pt;
        seller_code = s_code;
        ticket_num = t_num;
    }
	
    
    public void run(){
	   System.out.println("Writer Server running ... ");
		 try {
        ServerSocket writesock = new ServerSocket(port);
        while (true) {
          Socket socket = writesock.accept();
          writeData(socket);
        }
     } catch (IOException e) {e.printStackTrace();}
	}
	
    
    private void writeData(Socket sk) {
        try {
            DataOutputStream out = new DataOutputStream(sk.getOutputStream());

            out.writeUTF(seller_code);
            out.writeUTF(ticket_num);
            out.flush();
            out.close();
        } catch (IOException e) {
        }
    }
}
