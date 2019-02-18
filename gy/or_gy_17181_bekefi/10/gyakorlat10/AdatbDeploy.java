package gyakorlat10;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Random;


public class AdatbDeploy extends UnicastRemoteObject implements AdatbInterface {
    private Random rand = new Random(11111);
    
    public AdatbDeploy() throws RemoteException {
        super();
    }
    
    public static void main(String[] args) throws RemoteException, InterruptedException, NotBoundException {
        Registry reg = LocateRegistry.createRegistry(12345);
        AdatbDeploy adatb = new AdatbDeploy();
        reg.rebind("adatb", adatb);
        Thread.sleep(5000);
        
        reg.unbind("adatb");
        UnicastRemoteObject.unexportObject(adatb, true);
    }

    @Override
    public int nextNumber() throws RemoteException {
        int number = rand.nextInt(10)+1;
        return number;
    }
}
