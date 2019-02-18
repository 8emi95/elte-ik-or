import java.util.*;
import java.net.*;
import java.io.*;

public class Szerver {
    public static void main(String[] args) throws Exception {
        final int PORT = 12345;
        // final int PORT = 65535;

        final String param = args[0];

        try (
            ServerSocket ss = new ServerSocket(PORT);
            Socket s = ss.accept();
            Scanner sc = new Scanner(s.getInputStream());
            PrintWriter pw = new PrintWriter(s.getOutputStream());
        ) {
            // String be = sc.nextLine();
            // String be2 = sc.nextLine();
            // pw.println(be);
            // pw.println(be2);

            // int num = Integer.parseInt(be2);
            // for (int i = 0; i < num; ++i) {
            //     System.out.print(be + " ");
            // }
            // System.out.println();

            // pw.flush();
            // System.out.println(be + "kati");
            // System.out.println(be2);

            // F2 Kitlei
            int hanyszor = sc.nextInt();
            // Integer.parseInt(sc.nextLine());
            // String result = param;
            StringBuilder result = new StringBuilder();
            for (int i = 0; i < hanyszor; ++i) { // Stringgel 1től
                // pw.print(param);
                // result += param;
                result.append(param);
            }
            pw.println(result-toString());
            pw.flush();
        }
    }
}