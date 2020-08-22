import java.net.Socket;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.util.Date;

/**
 *
 * @author 
 */
public class DesignPattern8613 {
    public static void main(String[] args) {
        int portNumber = 8613;
        String hostName = "localhost"; 
        
        // Initialise the server
        ChatServer chatServer = new ChatServer();
        // start socket connection
        chatServer.startServer(portNumber);
        // Listen to incoming connections from client
        chatServer.listen();
        
        Client chatClient = new Client();
        // connect to server
        chatClient.connect(hostName, portNumber);
        chatClient.sendMessage("Hello to the world");
        
        
    }
}

class Client {
    private DataOutputStream toServer;
    private DataInputStream fromServer;
    
    public void connect(String hostName, int port){
        try {
        // Create a socket to connect to the server
        Socket socket = new Socket("localhost", port);

        // Create an input stream to receive data from the server
        fromServer = new DataInputStream(socket.getInputStream());

        // Create an output stream to send data to the server
        toServer = new DataOutputStream(socket.getOutputStream());
       }
       catch (IOException ex) {
         System.out.println(ex.toString() + '\n');
       }
    }
    
    public void sendMessage(String message){
        try{
            // Send the message to the server
            toServer.writeUTF(message);
            toServer.flush();
        }
        catch (IOException ex) {
            System.out.println(ex.toString() + '\n');
        }
    }
    
}

class ChatServer {
   
    private ServerSocket serverSocket;
    
    public void startServer(int portNumber)
    {
        try{
            // Create a server socket
            this.serverSocket = new ServerSocket(portNumber);
            System.out.println("MultiThread Chat Server started at " + new Date() + '\n');
        }catch (IOException ex) {
            System.out.println(ex.toString() + '\n');
        }
    }
   
   public void listen()
   {
       try{
            while (true) {
             // Listen for a new connection request
             Socket socket = this.serverSocket.accept();

             // Display the client number
             System.out.println("Starting thread for chat client at" + new Date() + '\n');

             // Find the client's host name, and IP address
             InetAddress inetAddress = socket.getInetAddress();
             System.out.println("Chat Client host name is " + inetAddress.getHostName() + "\n");
             System.out.println("Chat Client IP Address is " + inetAddress.getHostAddress() + "\n");


             // Start the new thread
             // broadcast the message to all hosts

           }
       }catch (IOException ex) {
            System.out.println(ex.toString() + '\n');
        }
       
   }
}
