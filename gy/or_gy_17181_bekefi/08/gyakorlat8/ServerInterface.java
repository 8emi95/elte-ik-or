package gyakorlat8;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServerInterface extends Remote {
    int elkezd(int id) throws RemoteException;
    void befejez(int id) throws RemoteException;
    void kijavit() throws RemoteException;
}
