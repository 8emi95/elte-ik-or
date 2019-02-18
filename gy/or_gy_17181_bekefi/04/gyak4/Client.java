package gyak4;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;


public class Client {
    
    public static void main(String[] args) throws IOException, InterruptedException {
        Socket socket = new Socket("localhost", 12345);
        PrintWriter pw = new PrintWriter(socket.getOutputStream(), true);
        for (int i=10; i>=0; i--) {
            pw.println(i);
        }
        Thread.sleep(1000);
        socket.close();
    }
}
