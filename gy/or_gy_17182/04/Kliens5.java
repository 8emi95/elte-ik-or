// 5.
// szerver -> tárolja hány kliens kapcsolódott már hozzá
// kliens kapcsolódik -> szerver írja ezt vissza neki + bontsa a kapcsolatot + várakozzon újabb kliensre

import java.util.*;
import java.io.*;
import java.net.*;

public class Kliens5 {
    public static void main(String[] args) throws Exception {
        final String GEP = "127.0.0.1";
        final int PORT = 12345;

        try (
            Socket s = new Socket(GEP, PORT);
            Scanner scSock = new Scanner(s.getInputStream());
            PrintWriter pwSock = new PrintWriter(s.getOutputStream());

            // Scanner scFile = new Scanner(new File("in.txt"));
            // PrintWriter pwFile = new PrintWriter(new File("out.txt"));
        ) {
            System.out.println(scSock.nextLine());
            scSock.close();
        }
    }
}