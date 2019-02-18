
import java.util.*;
import java.io.*;
import java.net.*;

public class Kliens3 {
    public static void main(String[] args) throws Exception {
        final String GEP = "127.0.0.1";
        final int PORT = 12345;

        try (
            Socket s = new Socket(GEP, PORT);
            Scanner scSock = new Scanner(s.getInputStream());
            PrintWriter pwSock = new PrintWriter(s.getOutputStream());

            Scanner scFile = new Scanner(new File("in.txt"));
            PrintWriter pwFile = new PrintWriter(new File("out.txt"));
        ) {
            while (scFile.hasNextInt()) {
                int num = scFile.nextInt();

                pwSock.println(num);
            }
            pwSock.println(0);
            pwSock.flush();

            while (scSock.hasNextInt()) {
                int num = scSock.nextInt();

                pwFile.println(num);
            }
            pwFile.flush();
        }
    }
}
