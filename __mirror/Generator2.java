/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package __mirror;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.*;

/**
 *
 * @author tohir
 */
public class Generator2 {

    private static final String CLIENT_CODE = "C002";
    private static final int PORT_NO = 1234;

    public static void main(String[] args) throws IOException {

        Ticket t1 = new Ticket(CLIENT_CODE, "12-13-15-21-33-28");
        Ticket t2 = new Ticket(CLIENT_CODE, "18-19-9-21-33-24");
        Ticket t3 = new Ticket(CLIENT_CODE, "2-13-15-1-30-27");

        // call server A
        ServerA a = new ServerA(PORT_NO);
        a.start();
//        a.doTicketUpload(t1);
//        a.doTicketUpload(t2);
//        a.doTicketUpload(t3);
        

        Socket sock = new Socket(InetAddress.getLocalHost(), PORT_NO);
        InputStream in = sock.getInputStream();
        
        DataOutputStream out = new DataOutputStream(sock.getOutputStream());
        out.writeUTF(CLIENT_CODE);
        out.writeUTF("12-13-15-21-33-28");
        
        out.writeUTF(CLIENT_CODE);
        out.writeUTF("18-19-9-21-33-24");
        
        System.out.println("Sent to Server...");
        
        // test data store size
//        a.fetchTickets(CLIENT_CODE);

    }
    
}
