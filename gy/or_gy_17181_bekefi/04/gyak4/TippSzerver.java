package gyak4;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;


public class TippSzerver {    
    public static void main(String[] args) throws IOException, InterruptedException {
        Random rand = new Random();
        int number = rand.nextInt(100)+1;
        System.out.println("A kitalalando szam: "+number);
        List<Szal2> threads = new ArrayList<>();
        ServerSocket server = new ServerSocket(55555);
        server.setSoTimeout(10000);
        try {
            while(true) {
                Socket client = server.accept();
                Szal2 sz = new Szal2(client, number);
                threads.add(sz);
                sz.start();
            }
        } catch(SocketTimeoutException ex) {
            for (Szal2 sz : threads) {
                sz.join();
            }
            server.close();
        }
    }
    
}

class Szal2 extends Thread {
    private Socket socket;
    private int number;
    
    public Szal2(Socket socket, int number) {
        this.socket = socket;
        this.number = number;
    }
    
    @Override
    public void run() {
        Scanner sc = null;
        PrintWriter pw = null;
        boolean end = false;
        try {
            sc = new Scanner(socket.getInputStream());
            pw = new PrintWriter(socket.getOutputStream(), true);
            while(!end) {
                String msg = sc.nextLine();
                int guess = Integer.parseInt(msg);
                if (number < guess) {
                    pw.println("Kisebb");
                }
                else if (number > guess) {
                    pw.println("Nagyobb");
                }
                else {
                    pw.println("Talalt");
                    end = true;
                }
            }
            socket.close();
        } catch(IOException ex) {
            System.out.println("Hiba a klienssel valo kommunikacio soran: "+ex);
        }
    }
}
