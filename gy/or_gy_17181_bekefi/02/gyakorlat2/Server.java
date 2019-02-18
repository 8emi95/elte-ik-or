package gyakorlat2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
 
    public static void main(String[] args) throws IOException {
        // Letrehozzuk az 12345 porton futo server socketet
        ServerSocket server = new ServerSocket(12345);
        System.out.println("A szerver elindult.");
        
        // A server fogadja a csatlakozo klienst es eltarolja
        Socket client = server.accept();
        System.out.println("Egy kliens csatlakozott.");
        
        // A BufferedReader segitsegevel lehet majd olvasni a csatornan a server fele erkezo adatfolyamot
        BufferedReader br = new BufferedReader(new InputStreamReader(client.getInputStream()));
        String message = br.readLine();
        System.out.println("Klienstol kapott uzenet: "+message);
        
        // A PrintWriter segitsegevel lehet majd irni a csatornara, uzenetet kuldeni a kliensnek
        PrintWriter pw = new PrintWriter(client.getOutputStream(), true);
        pw.println(message);
        
        // Lezarja a kapcsolatot a klienssel
        client.close();
        // Lezarja a server socketet
        server.close();
    }
}
