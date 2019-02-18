package verseny;

import java.io.*;
import java.net.*;
import java.util.*;

public class Client extends Thread {
    public static void main(String[] args) throws IOException {
        try {
            Scanner console = new Scanner(System.in);
            Socket s = new Socket(Server.HOST, Server.PORT);
            Scanner sc = new Scanner(s.getInputStream(), "utf-8");
            PrintWriter pw = new PrintWriter(s.getOutputStream());

            String name = console.nextLine();
            pw.println(name);
            pw.flush();

            while (true) {
                String msg = console.nextLine();
                pw.println(msg);
                pw.flush();

                if (msg.equals(Server.EXIT)) {
                    System.out.println("Closing this connection : " + s);
                    s.close();
                    System.out.println("Connection closed");
                    break;
                }
            }

            console.close();
            sc.close();
            pw.close();
        } catch (Exception e) {
            // e.printStackTrace();
            System.out.println("Connection closed");
        }
    }

}
