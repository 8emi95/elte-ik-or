import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;


public class Server {
    
    public static int PORT = 11223;
    
    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        ServerSocket ss = new ServerSocket(PORT);
        
        List<Thread> threads = new ArrayList<>();
        ss.setSoTimeout(10000);
        try {
            while(true) {
                Socket s = ss.accept();
                Handler h = new Handler(s);
                threads.add(h);
                h.start();
            }
        } catch(SocketTimeoutException ex) {
            for (Thread t : threads) {
                t.join();
            }
            System.out.println("A szerver leall.");
            ss.close();
        }
    }
}

class Handler extends Thread{
    private Socket socket;
    
    public Handler(Socket socket) {
        this.socket = socket;
    }
    
    @Override
    public void run() {
        try {
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            Object o = ois.readObject();
            System.out.println("Szerver: ezt a kort kaptam: "+o.toString());
            
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            Double k = ((Circle)o).getC();
            oos.writeObject(k);
            oos.flush();

            System.out.println("Szerver: ezt a keruletet kuldtem: "+k);
        } catch(IOException | ClassNotFoundException ex) {
            System.out.println("Hiba a kommunikacio soran: "+ex);
        }
    }
}
