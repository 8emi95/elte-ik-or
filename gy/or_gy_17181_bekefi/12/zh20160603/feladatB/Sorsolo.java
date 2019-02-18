package zh20160603.feladatB;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Scanner;


public class Sorsolo {
    
    public static void main(String[] args) throws RemoteException, NotBoundException, InterruptedException, IOException {
        Socket s = new Socket("localhost", 22222);
        Scanner sc = new Scanner(s.getInputStream());
        PrintWriter pw = new PrintWriter(s.getOutputStream(), true);
        while(true) {
            pw.println("sorsol");
            String answer = sc.nextLine();
            if (answer.contains("csod")) {
                System.out.println("Leall a sorsolo.");
                break;
            }
            Thread.sleep(1000);
        }
        
    }
}
