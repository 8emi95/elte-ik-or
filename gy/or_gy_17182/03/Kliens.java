// F3
// kliens: fájlból egész számokat olvas, szervernek küldi sorban
// szerver: (mondjuk n ↦ 2*n+1) fv minden számra, eredményt visszaküldi
// kliens: eredményeket egy fájlba írja ki sorban. Ha a 0 szám következne a kliensoldalon, akkor a kliens kilép.
// a) A kliens most küldje át az összes adatot a szervernek, és csak utána fogadja a visszaérkező számokat; hasonlóan, a szerver fogadja az összes számot, és csak utána küldje el őket átalakítva a kliensnek.
// b) A szerver várakozzon a kliens kilépése után új kliensre, és ez ismétlődjön a végtelenségig.

import java.util.*;
import java.net.*;
import java.io.*;

public class Kliens {
    public static void main(String[] args) throws Exception {
        // final String GEP = "localhost";
        final String GEP = "127.0.0.1";
        final int PORT = 12345;
        // final int PORT = 65535;

        try (
            Socket s = new Socket(GEP, PORT);
            Scanner scSock = new Scanner(s.getInputStream());
            PrintWriter pwSock = new PrintWriter(s.getOutputStream());
            
            Scanner scFile = new Scanner(new File("input.txt"));
            PrintWriter pwFile = new PrintWriter(new File("output.txt"));
        ) {
            // Kitlei meo
            while(scFile.hasnextInt()) {
                int num = scFile.nextInt();
                pwSock.println(num);
            }
            // pwSock.println(0);
            pwSock.flush();

            while(scSock.hasnextInt()) {
                int num scSock.nextInt();
                pwFile.println(num);
            }
            pwFile.flish();
        }
    }
}