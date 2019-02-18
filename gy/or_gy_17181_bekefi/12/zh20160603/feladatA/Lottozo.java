package zh20160603.feladatA;

import zh20160603.alapfeladat.*;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;


public class Lottozo extends UnicastRemoteObject implements LottozoIface {
    private Random rand;
    private final int MAX = 90;
    private final int DB = 5;
    private Set<Integer> nyeroszamok = new HashSet<>();
    private int kassza;
    
    public Lottozo(int kassza) throws RemoteException {
        super();
        rand = new Random(123456);
        this.kassza = kassza;
    }
    
    public static void main(String[] args) throws RemoteException {
        Lottozo lottozo = new Lottozo(Integer.parseInt(args[0]));
        Registry reg = LocateRegistry.createRegistry(8899);
        reg.rebind("lottozo", lottozo);
        System.out.println("A lottozo elindult "+args[0]+" kezdoosszeggel.");
    }

    @Override
    public synchronized void sorsol() throws RemoteException {
        if (kassza <= 0) throw new UnsupportedOperationException("Csodbe ment a lottozo."); 
        
        nyeroszamok.clear();
        while(nyeroszamok.size() < DB) {
            nyeroszamok.add(rand.nextInt(MAX)+1);
        }
        System.out.println("L > sorsolas: "+nyeroszamok);
    }

    @Override
    public synchronized int jatszik(Set<Integer> szamok) throws RemoteException {
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
