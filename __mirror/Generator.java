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
public class Generator {

    private static final String CLIENT_CODE = "C002";
    private static final int PORT_NO = 1234;

    public static void main(String[] args) throws IOException {

        // variable declaration
        String ticketNumbers;

        // init scanner
        Scanner input = new Scanner(System.in);
        System.out.println("Enter 5 digits number separated by -");
        // 11-4-9-21-36-40

        ticketNumbers = input.nextLine();

        // validate input
        String parts[] = ticketNumbers.split("-");
        // check if ticket numbers are 6 digits
        if (parts.length == 6) {
            for (int j = 0; j < parts.length; j++) {
                // check if each number is within acceptable range
                if (Integer.parseInt(parts[j]) < 1 || Integer.parseInt(parts[j]) > 40) {
                    System.out.println("ticket number out of range");
                }
            }

            Ticket t = new Ticket(CLIENT_CODE, ticketNumbers);
            
            // call server A
            ServerA a = new ServerA(PORT_NO);
            a.doTicketUpload(t);


        } else {
            System.out.println("Ticket must be 6 digits");
        }

    }
    
    static void populateTicket(){
        
    }
}
