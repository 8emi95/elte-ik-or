package garden;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Garden {

    public static final int WAIT_BETWEEN_THREADS = 1000;

    public static void main(String[] args) throws InterruptedException {
        new Thread() {
            @Override
            public void run() {
                try {
                    PlantFactory plantFactory = new PlantFactory("/home/rq/Desktop/ELTE/osztottr/beadando/src/garden/input.txt");
                    Registry reg = LocateRegistry.createRegistry(8888);
                    reg.rebind("factory", plantFactory);
                } catch(RemoteException ex) {
                    System.err.println("RemoteException") ;
                }
            }
        }.start();
        Thread.sleep(WAIT_BETWEEN_THREADS);

        new Thread() {
            @Override
            public void run() {
                try {
                    GardenStore.main(new String[]{"20", "filename"});
                } catch (NotBoundException | PlantWitheredException | InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }.start();
        Thread.sleep(WAIT_BETWEEN_THREADS);

        Customer.main(new String[]{"Customer1", "1", "5"});
        Customer.main(new String[]{"Customer2", "2", "4"});
        Customer.main(new String[]{"Customer3", "3", "2"});
        Thread.sleep(WAIT_BETWEEN_THREADS);


        Gardener.main(new String[]{"Gardener1", "4"});
        Gardener.main(new String[]{"Gardener2", "5"});
        Thread.sleep(WAIT_BETWEEN_THREADS);
    }
}