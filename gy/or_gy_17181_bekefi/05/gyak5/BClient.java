package gyak5;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BClient {
    
    public static void main(String[] args) throws IOException {
        Socket s = new Socket("localhost", 12345);
        PrintWriter pw = new PrintWriter(s.getOutputStream(), true);
        
        Random r = new Random();	
        for (int i=0; i<5; i++) {
            int sum = r.nextInt(1000);
            if (r.nextInt(2) == 0) {
                pw.println("kivesz "+sum);
                System.out.println("kivesz "+sum);
            }
            else {
                pw.println("berak "+sum);
                System.out.println("berak "+sum);
            }
            
            try {
                Thread.sleep((r.nextInt(3)+1)*1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(Handler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
