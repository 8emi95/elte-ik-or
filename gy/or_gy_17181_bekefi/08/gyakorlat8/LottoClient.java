package gyakorlat8;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;


public class LottoClient {
    
    public static void main(String[] args) throws RemoteException, NotBoundException {
        Registry registry = LocateRegistry.getRegistry(12345);
        String[] names = registry.list();
        
        for (String name : names) {
            LottoInterface li = (LottoInterface)registry.lookup(name);
            if (li.nyeroszamE()) {
                System.out.println(name);
            }
        }
    }
}
