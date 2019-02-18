// 5.
// szerver -> tárolja hány kliens kapcsolódott már hozzá
// kliens kapcsolódik -> szerver írja ezt vissza neki + bontsa a kapcsolatot + várakozzon újabb kliensre

import java.util.*;
import java.net.*;
import java.io.*;

public class Szerver5 {
    public static void main(String[] args) throws Exception {
        final int PORT = 12345;

        try (
            ServerSocket ss = new ServerSocket(PORT);
            Socket s        = ss.accept();
            Scanner sc      = new Scanner(s.getInputStream(), "utf-8");
            PrintWriter pw  = new PrintWriter(s.getOutputStream());
        ) {
            pw.println("Socket connected.");
            s.close();
        }
    }
}