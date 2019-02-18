import java.rmi.Remote;
import java.rmi.RemoteException;


public interface CircleRemote extends Remote { // az itteni fv-eknek mindene serializalhatonak kell lenni, kulonben futasi ideju kivetel
    
    public double getC(Circle c) throws RemoteException; // minden rmi-s fv-nek dobnia kell RemoteException-t

}
