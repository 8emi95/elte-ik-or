
import java.util.*;
import java.io.*;
import java.net.*;

public class Szerver1 {
    public static void main(String[] args) throws Exception {
        final int PORT = 12345;
        // final int PORT = 65535;

        try (
            ServerSocket ss = new ServerSocket(PORT);
            Socket s = ss.accept();
            Scanner sc = new Scanner(s.getInputStream());
            PrintWriter pw = new PrintWriter(s.getOutputStream());
        ) {
            String be = sc.nextLine();
            System.out.println(be);

            pw.println(be);
            pw.flush();
        }
    }
}
