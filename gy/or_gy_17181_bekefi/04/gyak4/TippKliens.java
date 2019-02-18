package gyak4;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Random;
import java.util.Scanner;

public class TippKliens {
    
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost", 55555);
        PrintWriter pw = new PrintWriter(socket.getOutputStream(), true);
        Scanner sc = new Scanner(socket.getInputStream());
        Random r = new Random();
        boolean end = false;
        
        do {
            int guess = r.nextInt(100)+1;
            pw.println(guess);
            String answer = sc.nextLine();
            System.out.println("Tipp: "+guess+", valasz: "+answer);
            end = answer.equals("Talalt");
        } while(!end);

        socket.close();
    }
    
   
}
