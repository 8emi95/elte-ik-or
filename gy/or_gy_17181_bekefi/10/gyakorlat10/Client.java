package gyakorlat10;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;



public class Client {
    
    public static void main(String[] args) throws IOException {
        Socket s = new Socket("localhost", 12345);
        Scanner sc = new Scanner(s.getInputStream());
        PrintWriter pw = new PrintWriter(s.getOutputStream(), true);
        System.out.println("A nevem: "+args[0]);
        pw.println(args[0]);
        boolean end = false;
        while(!end) {
            String message = sc.nextLine();
            if (message.equals("Nyertel")) {
                System.out.println(args[0]+": nyertem");
                end = true;
            }
            else {
                int number = Integer.parseInt(message);
                number--;
                pw.println(number);
                if (number < 0) {
                    System.out.println(args[0]+" kiestem.");
                }
            }
        }
    }
}
