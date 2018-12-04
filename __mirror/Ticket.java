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

}
