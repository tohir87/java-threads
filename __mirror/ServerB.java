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
public class ServerB {

    static DataStore store;
    static ArrayList<Ticket> data;

    public static void main(String[] args) throws IOException {

        data = new ArrayList<Ticket>();
        store = new DataStore(data);

        ServerSocket ss = new ServerSocket(1234);
        System.out.println("Server B is listening on port: 1234 ");

        while (true) {
            Socket sock = ss.accept();
            System.out.println("connected to a client...");

            DataInputStream input = new DataInputStream(sock.getInputStream());
            String seller = input.readUTF();
            String t_num = input.readUTF();
            Ticket tic = new Ticket(seller, t_num);
            System.out.println("recieved data from client:" + seller + t_num);

            // start the uploader thread
            new TicketUploader(store, tic).start();
            
            
            fetchTickets(seller);
        }

    }

   

    static void fetchTickets(String sellerCode) {
        System.out.println("size of data store" + data.size());
    }
}
