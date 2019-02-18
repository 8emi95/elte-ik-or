import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.HashMap;


public class KodDeploy {

    
    public static void main(String[] args) throws RemoteException {        
        Registry registry = LocateRegistry.createRegistry(12345);
        for (char c = 'a'; c <= 'z'; c++) {
            registry.rebind(Character.toString(c), new KodImpl(c, (2*c-10)));
        }
                
    }
    
}
