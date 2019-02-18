package zh20160603.feladatB;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.RemoteException;
import java.util.HashSet;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Lottozo {
    private Random rand;
    private final int MAX = 90;
    private final int DB = 5;
    private Set<Integer> nyeroszamok = new HashSet<>();
    private int kassza;
    
    public Lottozo(int kassza) {
        this.kassza = kassza;
        rand = new Random(123456);
    }
    
    public static void main(String[] args) throws RemoteException, IOException {
        Lottozo lottozo = new Lottozo(Integer.parseInt(args[0]));
        System.out.println("A lottozo elindult "+args[0]+" kezdoosszeggel.");
        
        ServerSocket server = new ServerSocket(22222);
        while(true) {
            new Handler(server.accept(), lottozo).start();
        }
    }

    public synchronized void sorsol() {
        if (kassza <= 0) throw new UnsupportedOperationException("Csodbe ment a lottozo."); 
        
        nyeroszamok.clear();
        while(nyeroszamok.size() < DB) {
            nyeroszamok.add(rand.nextInt(MAX)+1);
        }
        System.out.println("L > sorsolas: "+nyeroszamok);
    }

    public synchronized int jatszik(Set<Integer> szamok) {
        if (kassza <= 0) throw new UnsupportedOperationException("Csodbe ment a lottozo."); 
        
        int nyeremeny = -2;
        int talalat = 0;
        for (Integer szam : szamok) {
            if (nyeroszamok.contains(szam)) talalat++;
        }
        if (talalat > 1) {
            nyeremeny += talalat*5;
        }
        kassza -= nyeremeny;
        if (kassza <= 0)  throw new UnsupportedOperationException("Csodbe ment a lottozo."); 
        System.out.println("L > tipp: "+szamok+"; kifizetes: "+nyeremeny+"; új kassza: "+kassza);
        return nyeremeny;
    }
    
}

class Handler extends Thread {
    private Scanner sc;
    private PrintWriter pw;
    private static Lottozo lottozo;
    
    public Handler(Socket s, Lottozo lottozo) throws IOException {
        sc = new Scanner(s.getInputStream());
        pw = new PrintWriter(s.getOutputStream(), true);
        this.lottozo = lottozo;
    }
    
    @Override
    public void run() {
        while(true) {
            String message = sc.nextLine();
            if (message.contains("sorsol")) {
                try {
                    lottozo.sorsol();
                    pw.println("ok");
                } catch (UnsupportedOperationException ex) {
                    pw.println("csod");
                    //Logger.getLogger(Handler.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            else if (message.contains("jatszik")) {
                String[] msg = message.split(" ");
                Set<Integer> tippek = new HashSet<>();
                for (int i=1; i<msg.length; i++) {
                    tippek.add(Integer.parseInt(msg[i]));
                }
                try {
                    String answer = Integer.toString(lottozo.jatszik(tippek));
                    pw.println(answer);
                } catch (UnsupportedOperationException ex) {
                    pw.println("csod");
                    //Logger.getLogger(Handler.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
}
