package alapfeladat;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface AkasztofaInterface extends Remote {

    public String jatszik(String nev, char betu) throws RemoteException;
}
