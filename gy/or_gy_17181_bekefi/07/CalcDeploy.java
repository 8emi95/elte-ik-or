
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;


public class CalcDeploy {
    
    public static void main(String[] args) throws RemoteException {
        CalcImpl calcImpl = new CalcImpl();
        Registry reg = LocateRegistry.createRegistry(12345);
        //Registry reg = LocateRegistry.getRegistry();  // Ha nem a forrasbol inditjuk el a registry-t, akkor ezt kell hasznalni.
	// Parancssorbol registry inditasa: a projekt build/classes mappajaba kell menni es ott "start rmiregistry" parancsot kiadni (idezojelek nelkul). 
	// Parameterkent megadhato a port, ahol fusson a registry. Az alapertelmezett a 1099 port. Pl.: start rmiregistry 12345
        reg.rebind("calc", calcImpl);
        System.out.println("A szerver elindult.");
    }
}
