// http://kitlei.web.elte.hu/segedanyagok/felev/2018-2019-osz/osztott/osztott-feladatok.html
// Kliens-szerver
// 5. 
// A szerver tárolja el, hogy hány kliens kapcsolódott már hozzá.
// Amikor egy kliens kapcsolódik, a szerver írja ezt vissza neki,
// majd rögtön bontsa a kapcsolatot, és várakozzon újabb kliensre.


import java.util.*;
import java.net.*;
import java.io.*;

public class Kliens5 {
    public static void main(String[] args) throws Exception {
        final String MACHINE = "127.0.0.1";
        final int PORT = 12345;

        try (
            Socket s        = new Socket(MACHINE, PORT);
            Scanner sc      = new Scanner(s.getInputStream(), "utf-8");
            PrintWriter pw  = new PrintWriter(s.getOutputStream());
        ) {
            // pw.println("file.txt");
            // pw.flush();
        }
    }
}