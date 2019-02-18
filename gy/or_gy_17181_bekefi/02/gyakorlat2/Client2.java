package gyakorlat2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client2 {
    
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost", 12345);
        System.out.println("A kliens csatlakozott a szerverhez.");
        PrintWriter pw = new PrintWriter(socket.getOutputStream(), true);
        BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        
        Scanner sc = new Scanner(System.in);
        System.out.println("Adja meg a nevet: ");
        String name = sc.nextLine();
        pw.println(name);
        
        String message = "";
        String receive = "";
        do {
            receive = br.readLine();
            receive = receive.trim();
            System.out.println(name+" - szervertol kapott uzenet: "+receive);
            if (!receive.contains("quit")) {
                System.out.println("Irja be az elkuldendo uzenetet: ");
                message = sc.nextLine();
                message = message.trim();
                pw.println(message);
                System.out.println(name+" - kuldott uzenet: "+message);
            }
        } while(!receive.contains("quit") && !message.equals("quit"));
        
        socket.close();
    }
}
