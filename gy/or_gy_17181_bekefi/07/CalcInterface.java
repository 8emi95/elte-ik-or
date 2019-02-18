import java.rmi.Remote;
import java.rmi.RemoteException;


public interface CalcInterface extends Remote {
    public int add(int x) throws RemoteException;
    public int minus(int x) throws RemoteException;
    public int mul(int x) throws RemoteException;
    public int div(int x) throws RemoteException;
}
