package gyakorlat10;

import java.rmi.Remote;
import java.rmi.RemoteException;


public interface AdatbInterface extends Remote {
    
    public int nextNumber() throws RemoteException;
    
}
