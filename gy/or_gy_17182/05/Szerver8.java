/* 8. feladat
- két kliens egymás után (ugyanazon a porton)
- a szerver mindkét kapcsolatot egyszerre tartja nyitva
- kliensek: egy-egy sorban a saját nevüket küldik át
- kliensek: felváltva írhatnak be egy-egy sornyi szöveget
- szerver: beírt üzeneteket küldje át a másik kliensnek ilyen alakban: <másik kliens neve>: <másik kliens üzenete>
- egyik kliens bontja a kapcsolatot -> szerver zárja be a másik klienssel a kapcsolatot, lépjen ki.

a) egyik kliens bontja a kapcsolatot -> akkor a szerver várakozzon egy újabb kliensre, aki kapcsolódás után átveszi az előző helyét. Természetesen az új kliens is először a saját nevét küldi át.

b)
- tetszőlegesen sok kliens kapcsolódhat
- bármikor ki is léphetnek
- szerver:  - sorban engedi szóhoz jutni a klienseket, azonban
            - összegyűjti az üzeneteket
            - akkor küldi el az adott kliens számára szóló üzeneteket, amikor az éppen szóhoz jut
*/

import java.util.*;
import java.net.*;
import java.io.*;

public class Szerver8 {
    public static void main(String[] args) throws Exception {
        final int PORT = 12345;

        try (
            ServerSocket ss = new ServerSocket(PORT);
            Socket s1        = ss.accept();
            Socket s2        = ss.accept(); // amíg 2. kliens nincs addig try utáni nem fut le
            Scanner sc1      = new Scanner(s1.getInputStream(), "utf-8");
            Scanner sc2      = new Scanner(s2.getInputStream(), "utf-8");
            PrintWriter pw1  = new PrintWriter(s1.getOutputStream());
            PrintWriter pw2  = new PrintWriter(s2.getOutputStream());
        ) {
            System.out.println("server: running");
            String name1 = sc1.nextLine();
            System.out.println("server: client " + name1 + " connected");
            String name2 = sc2.nextLine();
            System.out.println("server: client " + name2 + " connected");

            String msg1 = "";
            String msg2 = "";

            while (sc1.hasNextLine() || sc2.hasNextLine()) {
                msg1 = sc1.nextLine();
                pw2.println(name1 + ": " + msg1);
                System.out.println(name1 + ": " + msg1);
                msg2 = sc2.nextLine();
                pw1.println(name2 + ": " + msg2);
                System.out.println(name2 + ": " + msg2);
            }

            pw1.flush();
            pw2.flush();
            // try {
            //     if (!sc.hasNext()) {
            //         throw new FileNotFoundException();
            //     } else {
            //         String fileName = sc.next();
            //         Scanner scFile = new Scanner(new File(fileName));
            //         while (scFile.hasNextLine()) {
            //             System.out.println(scFile.nextLine());
            //         }
            //         scFile.close();
            //     }
            //     sc.close();
            // } catch(FileNotFoundException e) {
            //     System.out.println("File not found.");
            // }
            //pw.flush(); // ha nem _println_ akk flush nem ad garanciát
        }
    }
}