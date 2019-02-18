package gyak3;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;


public class Client {
    
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost", 22222);
        Scanner sc = new Scanner(socket.getInputStream());
        PrintWriter pw = new PrintWriter(socket.getOutputStream(), true);
        while(true) {
            int number = sc.nextInt();
            number++;
            sc.nextLine();
            System.out.println("Kliens fogadta: "+number);
            pw.println(number);
            System.out.println("Kliens: "+number+", "+number++);
        }
    }
}
