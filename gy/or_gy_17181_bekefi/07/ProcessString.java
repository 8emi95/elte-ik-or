import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ProcessString extends Remote {
    
    public String process(String s) throws RemoteException;
}
