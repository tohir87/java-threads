/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thread;

import java.io.*;
import java.net.*;
import java.util.*;
import java.awt.*;
import java.util.concurrent.ConcurrentHashMap;
import javax.swing.*;

/**
 *
 */
public class MultiThreadServer extends JFrame {
    
    
    // Text area for displaying contents
    private JTextArea jta = new JTextArea();

    public static void main(String[] args) {
      new MultiThreadServer();
    }

  
    public MultiThreadServer() {
    // Place text area on the frame
    setLayout(new BorderLayout());
    add(new JScrollPane(jta), BorderLayout.CENTER);

    setTitle("Chat Server:: Threaded");
    setSize(500, 300);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setVisible(true); // It is necessary to show the frame here!

    try {
      // Create a server socket
      ServerSocket serverSocket = new ServerSocket(8613);
      jta.append("MultiThread Chat Server started at " + new Date() + '\n');

      // Number a client
      int clientNo = 1;
      Socket[] socketArray;
      socketArray = new Socket[10];

      while (true) {
        // Listen for a new connection request
        Socket socket = serverSocket.accept();
        socketArray[clientNo] = socket;

        // Display the client number
        jta.append("Starting thread for chat client " + clientNo +
          " at " + new Date() + '\n');

        // Find the client's host name, and IP address
        InetAddress inetAddress = socket.getInetAddress();
        jta.append("Chat Client " + clientNo + "'s host name is "
          + inetAddress.getHostName() + "\n");
        jta.append("Chat Client " + clientNo + "'s IP Address is "
          + inetAddress.getHostAddress() + "\n");

        // Create a new thread for the connection
        HandleAClient task = new HandleAClient(socket, clientNo, socketArray);

        // Start the new thread
        new Thread(task).start();

        // Increment clientNo
        clientNo++;
      }
    }
    catch(IOException ex) {
      System.err.println(ex);
    }
  }



// Define the thread class for handling new connection
  class HandleAClient implements Runnable {
    private Socket socket; // A connected socket
    private int clientNo;

    /** Construct a thread */
    public HandleAClient(Socket socket, int clientNo, Socket[] socketArray) {
      this.socket = socket;
      this.clientNo = clientNo;
      
    }

    /** Run a thread */
    public void run() {
      try {
        // Create data input and output streams
        DataInputStream inputFromClient = new DataInputStream(
          socket.getInputStream());
        DataOutputStream outputToClient = new DataOutputStream(
          socket.getOutputStream());
        
        // Continuously serve the client
        while (true) {
          // Receive message from the client
          String message = inputFromClient.readUTF();
          
          // broadcast the message to all clients
          outputToClient.writeUTF(message);

          jta.append("Message from Cient " + this.clientNo + ": " + message + '\n');
          
        }
      }
      catch(IOException e) {
        System.err.println(e);
      }
    }
    
    
  }
}
