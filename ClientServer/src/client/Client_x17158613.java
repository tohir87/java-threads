/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

/**
 *
 * @author munaw
 */
import java.io.*;
import java.net.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Client_x17158613 extends JFrame {
  // Text field for receiving radius
  private JTextField jtf = new JTextField();

  // Text area to display contents
  private JTextArea jta = new JTextArea();

  // IO streams
  private DataOutputStream toServer;
  private DataInputStream fromServer;

  public static void main(String[] args) {
    new Client_x17158613();
  }

  public Client_x17158613() {
    // Panel p to hold the label and text field
    JPanel p = new JPanel();
    p.setLayout(new BorderLayout());
    p.add(new JLabel("Enter Loan Details"), BorderLayout.WEST);
    p.add(jtf, BorderLayout.CENTER);
    jtf.setHorizontalAlignment(JTextField.RIGHT);

    setLayout(new BorderLayout());
    add(p, BorderLayout.NORTH);
    add(new JScrollPane(jta), BorderLayout.CENTER);

    jtf.addActionListener(new Listener()); // Register listener

    setTitle("Client_x17158613");
    setSize(500, 300);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setVisible(true); // It is necessary to show the frame here!

    try {
      // Create a socket to connect to the server
      Socket socket = new Socket("localhost", 8000);
      // Socket socket = new Socket("130.254.204.36", 8000);
      // Socket socket = new Socket("drake.Armstrong.edu", 8000);

      // Create an input stream to receive data from the server
      fromServer = new DataInputStream(
        socket.getInputStream());

      // Create an output stream to send data to the server
      toServer =
        new DataOutputStream(socket.getOutputStream());
    }
    catch (IOException ex) {
      jta.append(ex.toString() + '\n');
    }
  }

  private class Listener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
      try {
        // Get the radius from the text field
        double loanInfo = Double.parseDouble(jtf.getText().trim());

        // Send the radius to the server
        toServer.writeDouble(loanInfo);
        toServer.flush();

        // Get loan details from the server
        double year = Double.parseDouble(jtf.getText().trim().substring(1, 2));
        
        double totalRepayment = fromServer.readDouble();
        double monthlyRepayment = totalRepayment / (year * 12);

        // Display to the text area
        jta.append("Loan Request is " + loanInfo + "\n");
        jta.append("Total repayment received from the server is "
          + totalRepayment + '\n');
        jta.append("Monthly repayment received from the server is "
          + monthlyRepayment + '\n');
      }
      catch (IOException ex) {
        System.err.println(ex);
      }
    }
  }
}