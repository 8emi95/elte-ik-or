package zh20160603.feladatC;

import zh20160603.feladatA.*;
import zh20160603.alapfeladat.*;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Set;


public interface LottozoIface extends Remote {
    public void sorsol() throws RemoteException;
    public int jatszik(Set<Integer> szamok, String nev) throws RemoteException;
}
