package __mirror;

import java.io.*;


public class Ticket{
    private String ticketNumber;
    private String sellerCode;
    
    public Ticket(String code, String num){
        this.sellerCode = code;
        this.ticketNumber = num;
    }

    public String getTicketNumber() {
        return ticketNumber;
    }

    public String getSellerCode() {
        return sellerCode;
    }
    
    @Override
    public String toString() {
        return "Ticket{" + "ticketNumber=" + ticketNumber + ", sellerCode=" + sellerCode + '}';
    }
    
    //Methods used to read and write to streams over sockets
  public void writeOutputStream(DataOutputStream out) {
        try {
            out.writeUTF(this.sellerCode);
            out.writeUTF(this.ticketNumber.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void readInputStream(DataInputStream in) {
        try {
            this.sellerCode = in.readUTF();
            this.ticketNumber = in.readUTF();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    

}
