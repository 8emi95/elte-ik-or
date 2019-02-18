import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;


public class CalcImpl extends UnicastRemoteObject implements CalcInterface{
    private int number;
    
    public CalcImpl() throws RemoteException {
        super();
        System.out.println("A szerver letrejott.");
    }
    
    @Override
    public synchronized int add(int x) throws RemoteException {
        number = number + x;
        return number;
    }

    @Override
    public synchronized int minus(int x) throws RemoteException {
        number = number - x;
        return number;
    }

    @Override
    public synchronized int mul(int x) throws RemoteException {
        number = number * x;
        return number;
    }

    @Override
    public synchronized int div(int x) throws RemoteException {
        number = number / x;
        return number;
    }
    
}
