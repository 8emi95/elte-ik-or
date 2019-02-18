package zh20160603.alapfeladat;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
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
    
    public void jatszik() throws RemoteException, NotBoundException, FileNotFoundException, InterruptedException {
        Registry reg = LocateRegistry.getRegistry(8899);
        Remote remote = reg.lookup("lottozo");
        LottozoIface li = (LottozoIface) remote;
        Scanner sc = new Scanner(new FileReader(fileNev));
        
        while(penz >= 2) {
            Set<Integer> tipp = new HashSet<>();
            for (int i=0; i<5; i++) {
                tipp.add(sc.nextInt());
            }
            try {
                int nyeremeny = li.jatszik(tipp);
                penz += nyeremeny;
                System.out.println(nev+"> tipp: "+tipp.toString() +
                        "; kifizetes: "+nyeremeny+"; penzem: "+penz);
            } catch(UnsupportedOperationException e) {
                System.out.println(nev+"> A lottozas befejezodott, mert a lottozo csodbe ment.");
                break;
            }
            Thread.sleep(1000);
        }
        if (penz < 2) {
            System.out.println(nev+"> A lottozas befejezodott, mert elfogyott a penzem.");
        }
    }
}
