// 4.
// klines -> szerver: fájlnév
// szerver -> kliens: fájl létezik ? fájl tartalma soronként : szöveges hibaüzenet

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
            try {
                if (!sc.hasNext()) {
                    throw new FileNotFoundException();
                } else {
                    String fileName = sc.next();
                    Scanner scFile = new Scanner(new File(fileName));
                    while (scFile.hasNextLine()) {
                        System.out.println(scFile.nextLine());
                    }
                    scFile.close();
                }
                sc.close();
            } catch(FileNotFoundException e) {
                System.out.println("File not found.");
            }
            //pw.flush();
        }
    }
}