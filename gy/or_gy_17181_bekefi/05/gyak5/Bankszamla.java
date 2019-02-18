package gyak5;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Bankszamla {
    private static int egyenleg;
    
    public static void main(String args[]) throws InterruptedException, IOException {
        int egyenleg = Integer.parseInt(args[0]);
        int felhasznalok = Integer.parseInt(args[1]);
        List<Thread> threads = new LinkedList<>();
        ServerSocket server = new ServerSocket(12345);
        
        for (int i=0; i<felhasznalok; i++) {
            ClientHandler h = new ClientHandler(server.accept());
            threads.add(h);
        }

        for (Thread t : threads) {
                t.start();
        }
        for (Thread t : threads) {
            t.join();
        }
                
        System.out.println("A program befejezodott.");
    }
    
    public static synchronized void kivesz(int osszeg) {
        if (osszeg > egyenleg) {
            egyenleg = 0;
        }
        else {
            egyenleg = egyenleg - osszeg;
        }
        System.out.println("Kivesz, összeg: "+osszeg+", uj egyenleg: "+egyenleg);
    }
    
    public static synchronized void berak(int osszeg) {
        egyenleg = egyenleg + osszeg;	
        System.out.println("Berak, összeg: "+osszeg+", uj egyenleg: "+egyenleg);
    }
}

class ClientHandler extends Thread {
    private Socket s;

    public ClientHandler(Socket s) {
        this.s = s;
    }

    @Override
    public void run() {
        try {
            Scanner sc = new Scanner(s.getInputStream());
            for (int i=0; i<5; i++) {
                String msg = sc.nextLine();
                String[] data = msg.split(" ");
                if (data[0].equals("kivesz")) {
                    Bankszamla.kivesz(Integer.parseInt(data[1]));
                } 
                else if (data[0].equals("berak")) {
                    Bankszamla.berak(Integer.parseInt(data[1]));
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    
}