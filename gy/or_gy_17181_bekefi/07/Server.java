import java.rmi.*;
import java.rmi.server.*;
import java.rmi.registry.*;

public class Server extends UnicastRemoteObject implements CircleRemote, ProcessString {

    public Server() throws RemoteException {
        super();
        System.out.println("A szerver peldany letrejott.");
    }
    
    @Override
    public double getC(Circle c) throws RemoteException {
        return c.getC();
    }
    
    public static void main(String[] args) throws RemoteException {
        Server server = new Server();
        Registry reg = LocateRegistry.createRegistry(1099); // 1099 az alapertelmezett portja a registry-nek
        reg.rebind("myserver", server); // sima bind() - ha mar letezik ilyen nevu, akkor exception. ha a letezot felulirnank -> rebind()
        System.out.println("A szerver elindult.");
        // vegtelen ideig futo, kereseket parhuzamosan kiszolgalo szerver indult el
    }

    @Override
    public String process(String s) throws RemoteException {
        return new StringBuilder(s).reverse().toString();
    }

}
