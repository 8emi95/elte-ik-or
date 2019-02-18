// http://kitlei.web.elte.hu/segedanyagok/felev/2018-2019-osz/osztott/osztott-feladatok.html
// Kliens-szerver
// 5. 
// A szerver tárolja el, hogy hány kliens kapcsolódott már hozzá.
// Amikor egy kliens kapcsolódik, a szerver írja ezt vissza neki,
// majd rögtön bontsa a kapcsolatot, és várakozzon újabb kliensre.


import java.util.*;
import java.net.*;
import java.io.*;

public class Szerver5 {
    public static void main(String[] args) throws Exception {
        final int PORT = 12345;

        int c = 0;
        try (
            ServerSocket ss = new ServerSocket(PORT);

            while (true) {
                Socket s        = ss.accept();
                Scanner sc      = new Scanner(s.getInputStream(), "utf-8");
                PrintWriter pw  = new PrintWriter(s.getOutputStream());
            }
        ) {
            c++;
            System.out.println("Client connected. Disconnecting.");
            pw.println("Client connected. Disconnecting.");
            pw.flush();
        }
        System.out.println("Total number of clients connected: " + c);
    }
}