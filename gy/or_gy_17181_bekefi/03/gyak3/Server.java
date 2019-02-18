package gyak3;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Server {

    
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        
        try {
            ServerSocket server = new ServerSocket(22222);
            System.out.println("A szerver elindult, kliensek: "+n);
            List<Socket> clients = new ArrayList<>();
            List<Scanner> scs = new ArrayList<>();
            List<PrintWriter> pws = new ArrayList<>();
            for (int i=0; i<n; i++) {
                clients.add(server.accept());
                scs.add(new Scanner(clients.get(i).getInputStream()));
                pws.add(new PrintWriter(clients.get(i).getOutputStream(), true));
                System.out.println("Egy kliens csatlakozott");
            }
            int index = 0;
            int number = 0;
            while(clients.size() > 0) {
                try {
                    pws.get(index).println(number);
                        System.out.println("Szerver elkuldte: "+index+": "+number);
                    number = scs.get(index).nextInt();
                    scs.get(index).nextLine();
                    System.out.println("Szerver: "+index+": "+number);
                } catch(NoSuchElementException e) {
                    clients.remove(index);
                    scs.remove(index);
                    pws.remove(index);
                    System.out.println("Kilepett: "+index);
                    if (index == clients.size()) {
                        index = -1;
                    }
                    else {
                        index = index-1;
                    }
                }
                if(clients.size() > 0) {
                    index = index+1;
                    index = index % clients.size();
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
