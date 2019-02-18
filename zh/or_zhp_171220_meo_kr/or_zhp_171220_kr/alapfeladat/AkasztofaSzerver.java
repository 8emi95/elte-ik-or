package alapfeladat;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class AkasztofaSzerver {

    public static void main(String[] args) throws RemoteException {
        Registry reg = LocateRegistry.createRegistry(9999);
        reg.rebind("akasztofa", new AkasztofaImpl(args[0]));
    }
}

