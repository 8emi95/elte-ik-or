// http://kitlei.web.elte.hu/segedanyagok/felev/2018-2019-osz/osztott/osztott-feladatok.html
// Kliens-szerver
// 2.
// A szerver paraméterként kapjon egy szöveget is.
// A kliens küldjön át egy számot;
// a szerver egy sorban válaszoljon egy olyan szöveggel,
// amely a paraméterként kapott szövegét ennyiszer tartalmazza.
// a) A feladatnak van egy egyszerű nem túl hatékony
//   (azaz: rossz futási karakterisztikájú) megoldása.
//   Ha a kliens által küldött szám n,
//   akkor milyen nagyságrendű lépést tesz meg
//   (és mekkora memóriát foglal) ez a megoldás?
// b) Van két hatékony megoldás is, ami természetesen adódik,
//    az egyik hasonlít az előzőleg említett megoldásra, a másik nem.
//    - Ezek milyen nagyságrendűek?
//    - Próbáld ki sok adattal őket annak igazolására,
//      hogy sokkal hatékonyabbak.
//    - A kettő közül hatékonyabb-e egyik a másiknál?

import java.util.*;
import java.net.*;
import java.io.*;

public class Szerver2 {
    public static void main(String[] args) throws Exception {
        // final String param = args[0]; // szöveg

        final int PORT = 12345;
        // final int PORT = 65535;

        try (
            ServerSocket ss = new ServerSocket(PORT);
            Socket s        = ss.accept();
            Scanner sc      = new Scanner(s.getInputStream(), "utf-8");
            PrintWriter pw  = new PrintWriter(s.getOutputStream());
        ) {
            // // szöveg parancssori paraméterként
            // String text = args[0];

            // if (sc.hasNextInt()) {
            //     // számot kap klienstől
            //     int num = sc.nextInt();
            //     // visszaküldi ennyiszer a parancssori szöveget
            //     System.out.println("from client: " + num);
            //     System.out.print("to client: ");
            //     for (int i = 0; i < num; ++i) {
            //         pw.print(text + " ");
            //         System.out.print(text + " ");
            //     }
            // }
            // pw.println();
            // pw.flush();

            while (sc.hasNextInt()) {
                int num = sc.nextInt();
                pw.println(convert(num));
            }
        }
    }

    private int convert(int num) {
        return 2 * num + 1;
    }
}