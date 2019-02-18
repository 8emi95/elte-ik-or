import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;


public class Client {
    
    public static void main(String[] args) throws RemoteException, NotBoundException {
        Registry reg = LocateRegistry.getRegistry(12345);
        Remote remote = reg.lookup("calc");
        CalcInterface ci = (CalcInterface) remote;
        System.out.println(ci.add(3));
        System.out.println(ci.minus(1));
        System.out.println(ci.mul(4));
        System.out.println(ci.div(2));
    }
}
