package gyakorlat2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server2 {
 
    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(12345);
        System.out.println("A szerver elindult.");
        Socket client1 = server.accept();
        Socket client2 = server.accept();

        BufferedReader br1 = new BufferedReader(new InputStreamReader(client1.getInputStream()));
        PrintWriter pw1 = new PrintWriter(client1.getOutputStream(), true);
        BufferedReader br2 = new BufferedReader(new InputStreamReader(client2.getInputStream()));
        PrintWriter pw2 = new PrintWriter(client2.getOutputStream(), true);

        String name1 = br1.readLine();
        String name2 = br2.readLine();

        String message1 = "";
        String message2 = "";
        
        pw1.println("start");
        do {
            message1 = br1.readLine();
            message1 = message1.trim();
            System.out.println(name1+": "+message1);
            pw2.println(name1+": "+message1);
            if (!message1.equals("quit")) {
                message2 = br2.readLine();
                message2 = message2.trim();
                System.out.println(name2+": "+message2);
                pw1.println(name2+": "+message2);
            }
        } while(!(message1.equals("quit") || message2.equals("quit")));
        
        client1.close();
        client2.close();
        server.close();
        
    }
}
