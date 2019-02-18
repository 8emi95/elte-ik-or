package gyakorlat8;

import java.rmi.Remote;
import java.rmi.RemoteException;


public interface LottoInterface extends Remote {
    boolean nyeroszamE() throws RemoteException;
}
