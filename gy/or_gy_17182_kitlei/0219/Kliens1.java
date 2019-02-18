
import java.util.*;
import java.io.*;
import java.net.*;

public class Kliens1 {
    public static void main(String[] args) throws Exception {
        // final String GEP = "localhost";
        final String GEP = "127.0.0.1";
        final int PORT = 12345;
        // final int PORT = 65535;

        try (
            Socket s = new Socket(GEP, PORT);
            Scanner sc = new Scanner(s.getInputStream());
            PrintWriter pw = new PrintWriter(s.getOutputStream());
        ) {
            pw.println("jguilshfjkdsghjfk");
            pw.flush();

            String be = sc.nextLine();
            System.out.println(be);
        }
    }
}
