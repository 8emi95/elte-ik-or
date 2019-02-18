// http://kitlei.web.elte.hu/segedanyagok/felev/2018-2019-osz/osztott/osztott-feladatok.html
// Párhuzamosság
// 4.
// a) 
// Készíts olyan chat alkalmazást,
// amelyben a két kliensnek nem kell egymásra várnia soronként,
// hanem bármikor beszélhetnek egymáshoz,
// és ez azonnal kiíródik a másik kliensnél.

// b)
// Készíts chat szervert, amelyhez tetszőleges számú kliens kapcsolódhat,
// illetve bármikor bonthatják is a kapcsolatot.
// Minden kliens először a nevét közli egy sorban.
// A kliensek bármikor beszélhessenek,
// ami azonnal íródjon ki minden kapcsolódott kliensnél.

// c)
// A szerver, ha két parancssori paramétert kap,
// akkor a megadott gépen/porton levő másik szerverhez kapcsolódik,
// és névként “kliens” szerver nevet küld át;
// ebből a másik szerver tudja, hogy szerver kapcsolódott hozzá.
// A rendszer továbbra is működjön chat-szerverként,
// azaz bármelyik szerver bármelyik kliense üzenetét kapja meg
// mindegyik olyan kliens, aki a rendszerbe tartozik.

import java.util.*;
import java.net.*;
import java.io.*;

public class Parh4 {
    public static void main(String[] args) throws Exception {
        try (
            ServerSocket ss = new ServerSocket(12345);

            Socket s1        = ss.accept();
            Scanner sc1      = new Scanner(s1.getInputStream(), "utf-8");
            PrintWriter pw1  = new PrintWriter(s1.getOutputStream());

            Socket s2        = ss.accept();
            Scanner sc2      = new Scanner(s2.getInputStream(), "utf-8");
            PrintWriter pw2  = new PrintWriter(s2.getOutputStream());
        ) {
            Thread thread1 = new Thread(() -> {
                while (sc1.hasNextLine()) {
                    String msg1 = sc1.nextLine();
                    pw1.println(msg1);
                    pw1.flush();
                }
            });

            Thread thread2 = new Thread(() -> {
                while (sc2.hasNextLine()) {
                    String msg2 = sc2.nextLine();
                    pw2.println(msg2);
                    pw2.flush();
                }
            });

            thread1.start();
            thread2.start();

            thread1.join();
            thread2.join();

            System.out.println("Finished."); // ha ilyen nincs, nem kell join
        }
    }
}