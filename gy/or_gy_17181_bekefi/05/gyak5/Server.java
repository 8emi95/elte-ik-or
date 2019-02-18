package gyak5;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server {
    private static int longest;
    
    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(12345);
        while(true) {
            Socket c1 = server.accept();
            Socket c2 = server.accept();
            Handler h = new Handler(c1, c2);
            h.start();
        }
    }
    
    public static synchronized void setLongest(int length) {
        if (length > longest) {
            longest = length;
        }
    }
    
    public static synchronized int getLongest() {
        return longest;
    }
}

class Handler extends Thread {
    private Socket c1;
    private Socket c2;
    
    public Handler(Socket c1, Socket c2) {
        this.c1 = c1;
        this.c2 = c2;
    }
    
    @Override
    public void run() {
        try {
            Scanner sc1 = new Scanner(c1.getInputStream());
            Scanner sc2 = new Scanner(c2.getInputStream());
            PrintWriter pw1 = new PrintWriter(c1.getOutputStream(), true);
            PrintWriter pw2 = new PrintWriter(c2.getOutputStream(), true);
            int counter = 0;
            String msg1;
            String msg2 = "";
            pw1.println("start");
            do {
                msg1 = sc1.nextLine();
                counter++;
                pw2.println(msg1);
                counter++;
                if (!msg1.equals("start")) {
                    msg2 = sc2.nextLine();
                    counter++;
                    pw1.println(msg2);
                    counter++;
                }
            } while(!msg1.equals("start") && !msg2.equals("start"));
            c1.close();
            c2.close();
            Server.setLongest(counter);
            System.out.println("Aktualis: "+counter+", leghosszabb: "+Server.getLongest());
        } catch (IOException ex) {
            Logger.getLogger(Handler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}