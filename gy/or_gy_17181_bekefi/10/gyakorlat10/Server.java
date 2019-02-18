package gyakorlat10;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;


public class Server {

    
    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(12345);
        List<Socket> clients = new ArrayList<>();
        
        int counter = 0;
        while(true) {
            clients = new ArrayList<>();
            while(counter < 3) {
                clients.add(server.accept());
                counter++;
            }
            new Handler(clients).start();
            counter = 0;
        }
        
    }
    
}

class Handler extends Thread {
    private List<Socket> clients;
    private List<Scanner> scs;
    private List<PrintWriter> pws;
    private List<String> names;
    private int number;
    
    public Handler(List<Socket> clients) throws IOException {
        this.clients = clients;
        scs = new ArrayList<>();
        pws = new ArrayList<>();
        names = new ArrayList<>();
        for (Socket s : clients) {
            scs.add(new Scanner(s.getInputStream()));
            pws.add(new PrintWriter(s.getOutputStream(), true));
        }
        Random r = new Random();
        number = r.nextInt(10)+10;
        System.out.println("A sorsolt szam: "+number);
    }
    
    @Override 
    public void run() {
        readName();
        boolean end = false;
        int index = 0;
        pws.get(index).println(number);
        while(!end) {
            String answer = scs.get(index).nextLine();
            System.out.println(names.get(index)+": "+answer);
            int answerNumber = Integer.parseInt(answer);
            if (answerNumber < 0) {
                int newIndex = index;
                if (index == clients.size()-1) {
                    newIndex = 0;
                }
                wrongAnswer(index);
                if (clients.size() == 1) {
                    pws.get(0).println("Nyertel");
                    end = true;
                }
                index = newIndex;
                restart();
                pws.get(index).println(number);
            }
            else {
                index++;
                index = index % clients.size();
                pws.get(index).println(answer);
            }
        }
    }
    
    public void readName() {
        for (Scanner sc : scs) {
            names.add(sc.nextLine());
        }
    }
    
    public void wrongAnswer(int index) {
        System.out.println(names.get(index)+" kiesett.");
        clients.remove(index);
        scs.remove(index);
        pws.remove(index);
    }
    
    public void restart() {
        Random r = new Random();
        number = r.nextInt(10)+10;
    }
}