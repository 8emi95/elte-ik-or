package zh20160603.feladatB;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;


public class Ember {
    
    private String nev;
    private int penz;
    private String fileNev;
    
    public Ember(String nev, int penz, String fileNev) {
        this.nev = nev;
        this.penz = penz;
        this.fileNev = fileNev;
    }
    
    public static void main(String[] args) throws Exception {
        Ember ember = new Ember(args[0], Integer.parseInt(args[1]), args[2]);
        ember.jatszik();
    }
    
    public void jatszik() throws RemoteException, NotBoundException, FileNotFoundException, InterruptedException, IOException {
        Socket s = new Socket("localhost", 22222);
        Scanner scSocket = new Scanner(s.getInputStream());
        PrintWriter pw = new PrintWriter(s.getOutputStream(), true);
        
        Scanner sc = new Scanner(new FileReader(fileNev));
        
        while(penz >= 2) {
            
            String tipp = "jatszik";
            for (int i=0; i<5; i++) {
                tipp += " "+sc.nextInt();
            }
            pw.println(tipp);
            String answer = scSocket.nextLine();
            if (answer.contains("csod")) {
                System.out.println(nev+"> A lottozas befejezodott, mert a lottozo csodbe ment.");
                break;
            }
            int nyeremeny = Integer.parseInt(answer);
            penz += nyeremeny;
            System.out.println(nev+"> tipp: "+tipp +
                    "; kifizetes: "+nyeremeny+"; penzem: "+penz);
            Thread.sleep(1000);
        }
        if (penz < 2) {
            System.out.println(nev+"> A lottozas befejezodott, mert elfogyott a penzem.");
        }
    }
}
