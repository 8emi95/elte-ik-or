
import java.util.*;
import java.io.*;
import java.net.*;

public class Szerver3 {
    public static void main(String[] args) throws Exception {
        final int PORT = 12345;

        try (
            ServerSocket ss = new ServerSocket(PORT);
        ) {
            while (true) {
                processNewClient(ss);
            }
        }
    }

    private static void processNewClient(ServerSocket ss) throws Exception {
        try (
            Socket s = ss.accept();
            Scanner sc = new Scanner(s.getInputStream());
            PrintWriter pw = new PrintWriter(s.getOutputStream());
        ) {
            while (sc.hasNextInt()) {
                int num = sc.nextInt();

                if (num == 0) {
                    break;
                }

                int result = f(num);

                pw.println(result);
                // pw.flush();

                // pw.print(result);
                // pw.print(result + " ");
            }
            // pw.println();
            pw.flush();

            // while (sc.hasNextLine()) {
            //     int num = Integer.parseInt(sc.nextLine());
            // }

            // String sor = sc.nextLine();
            // String[] szamokSzovegesen = sor.split(" ");
            // for (int szamSzovegesen : szamokSzovegesen) {
            //     int szam = Integer.parseInt(szamSzovegesen);
            // }

        }
    }


    public static int f(int n) { return 2 * n + 1; }
}