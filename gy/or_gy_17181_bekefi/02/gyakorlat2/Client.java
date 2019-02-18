package gyakorlat2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;


public class Client {
    
    public static void main(String[] args) throws IOException {
        // Kapcsolodik a kliens socket a localhost-on, az 12345 porton futo szerverhez
        // A szerver host-jet (ip-jet) String-kent kell megadni, a portot amin fut, pedig egesz szamkent
        Socket socket = new Socket("localhost", 12345);
        
        System.out.println("A kliens csatlakozott a szerverhez.");
        PrintWriter pw = new PrintWriter(socket.getOutputStream(), true);
        pw.println("uzenet");
        BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        String message = br.readLine();
        System.out.println("Szervertol kapott uzenet: "+message);
        
        // Bontja a kapcsolatot a szerverrel, lezarja a socketet
        socket.close();
    }
}
