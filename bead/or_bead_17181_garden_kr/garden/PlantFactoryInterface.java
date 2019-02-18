package garden;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface PlantFactoryInterface extends Remote {
    Plant getPlant() throws RemoteException, PlantWitheredException;
}
