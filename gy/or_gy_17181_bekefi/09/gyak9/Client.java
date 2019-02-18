package gyak9;


import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Client {
    
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost", 23456);
        Scanner sc = new Scanner(socket.getInputStream());
        PrintWriter pw = new PrintWriter(socket.getOutputStream(), true);
        pw.println("name2 pw2");
        System.out.println(sc.nextLine());
        
    }
}
