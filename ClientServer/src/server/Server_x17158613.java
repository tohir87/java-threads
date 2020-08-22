/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.awt.BorderLayout;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 *
 * @author tohir
 */
public class Server_x17158613 extends JFrame  {
    // Text area for displaying contents
  private JTextArea jta = new JTextArea();

  public static void main(String[] args) {
    new Server_x17158613();
  }

  public Server_x17158613() {
    // Place text area on the frame
    setLayout(new BorderLayout());
    add(new JScrollPane(jta), BorderLayout.CENTER);

    setTitle("Server_x17158613");
    setSize(500, 300);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setVisible(true); // It is necessary to show the frame here!

    try {
      // Create a server socket
      ServerSocket serverSocket = new ServerSocket(8000);
      jta.append("Server_x17158613 launched at " + new Date() + '\n');

      // Listen for a connection request
      Socket socket = serverSocket.accept();

      // Create data input and output streams
      DataInputStream inputFromClient = new DataInputStream(
        socket.getInputStream());
      DataOutputStream outputToClient = new DataOutputStream(
        socket.getOutputStream());

      while (true) {
        // Receive loan details from the client
        double radius = inputFromClient.readDouble();
        String loanDetails = Double.toString(radius);
        System.out.println(loanDetails);
        double interest = Double.parseDouble(loanDetails.substring(0, 1));
        System.out.println("Interest: " + interest);
        double year = Double.parseDouble(loanDetails.substring(1, 2));
        System.out.println("year: " + year);
        double amount = Double.parseDouble(loanDetails.substring(2, 6));
        System.out.println("amount: " + amount);
        
        double totalRepayment = (amount * (interest/100) ) + amount;
        System.out.println("Total repayment: " + totalRepayment);
        double monthlyRepayment = totalRepayment / (year * 12);
        System.out.println("Montly repayment: " + monthlyRepayment);
        

        // Send area back to the client
        outputToClient.writeDouble(totalRepayment);

        jta.append("interest received from client: " + interest + '\n');
        jta.append("year received from client: " + year + '\n');
        jta.append("amount received from client: " + amount + '\n');
        jta.append("Total repayment: " + totalRepayment + '\n');
        jta.append("Montly repayment: " + monthlyRepayment + '\n');
      }
    }
    catch(IOException ex) {
      System.err.println(ex);
    }
  }
}
