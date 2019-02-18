package zh20160603.feladatC;

import zh20160603.feladatA.*;
import zh20160603.alapfeladat.*;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;


public class Sorsolo {
    
    public static void main(String[] args) throws RemoteException, NotBoundException, InterruptedException {
        Registry reg = LocateRegistry.getRegistry(8899);
        Remote remote = reg.lookup("lottozo");
        LottozoIface lif = (LottozoIface) remote;
        
        while(true) {
            try {
                lif.sorsol();
            } catch(UnsupportedOperationException e) {
                System.out.println("Leall a sorsolo.");
                break;
            }
            Thread.sleep(1000);
        }
        
    }
}
