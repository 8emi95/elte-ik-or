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

public class Kliens2 {
    public static void main(String[] args) throws Exception {
        // final String MACHINE = "localhost";
        final String MACHINE = "127.0.0.1";
        final int PORT = 12345;
        // final int PORT = 65535;

        try (
            Socket s        = new Socket(MACHINE, PORT);
            Scanner sc      = new Scanner(s.getInputStream(), "utf-8");
            PrintWriter pw  = new PrintWriter(s.getOutputStream());

            // Scanner scIn    = new Scanner(System.in);
        ) {
            // // számot küld át
            // final int NUM = 5;
            // System.out.println("to server:: " + NUM);
            // pw.println(NUM);
            // pw.flush();

            // // kapott válasz
            // if (sc.hasNext()) {
            //     String answer = sc.nextLine();
            //     System.out.println("from server: " + answer);
            // }

            //
        }
    }
}