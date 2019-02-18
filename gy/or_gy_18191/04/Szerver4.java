// http://kitlei.web.elte.hu/segedanyagok/felev/2018-2019-osz/osztott/osztott-feladatok.html
// Kliens-szerver
// 4.
// A kliens átküld egy fájlnevet a szervernek.
// A szerver küldje vissza a fájl tartalmát soronként,
// ha a fájl létezik, különben pedig egy szöveges hibaüzenetet.


import java.util.*;
import java.net.*;
import java.io.*;

public class Szerver4 {
    public static void main(String[] args) throws Exception {
        final int PORT = 12345;

        try (
            ServerSocket ss = new ServerSocket(PORT);
            Socket s        = ss.accept();
            Scanner sc      = new Scanner(s.getInputStream(), "utf-8");
            PrintWriter pw  = new PrintWriter(s.getOutputStream());
        ) {
            while (sc.hasNextLine()) {
                File file = new File(sc.nextLine());
                // pw.println(file);
                System.out.println(file);

                if (file.exists()) {
                    try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                        while (true) {
                            String line = "";
                            while ((line = br.readLine()) != null) {
                                System.out.println(line);
                                pw.println(line);
                                pw.flush();
                            }
                        }
                    }
                } else {
                    System.out.println("The file does not exist.");
                    pw.println("The file does not exist.");
                    pw.flush();
                }
            }
        }
    }
}