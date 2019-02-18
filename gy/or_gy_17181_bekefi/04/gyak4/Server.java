package gyak4;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Server {
    
    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(12345);
        System.out.println("A szerver elindult.");
        while (true) {
            Socket client = server.accept();
            System.out.println("Egy kliens csatlakozott.");
            new ClientHandler(client).start();
        }
    }
}

class ClientHandler extends Thread {
    private Socket socket;
    private Scanner sc;
    
    public ClientHandler(Socket socket) {
        try {
            this.socket = socket;
            //this.sc = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.sc = new Scanner(socket.getInputStream());
        } catch (IOException ex) {
            Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void run() {
        boolean end = false;
        try {
            int x;
            do {
                x = sc.nextInt();
                //x = Integer.parseInt(sc.readLine());
                System.out.println("Kapott ertek: " + x);
            } while(x > 0);
            socket.close();
        } catch (IOException e) {
            System.out.println("Hiba a klienssel valo kommunikacio soran.");
        }
    }
}
