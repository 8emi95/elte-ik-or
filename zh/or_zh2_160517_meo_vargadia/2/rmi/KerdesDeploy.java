
package rmi;

import java.rmi.RemoteException;
import java.rmi.registry.*;
import java.util.logging.Level;
import java.util.logging.Logger;


public class KerdesDeploy {
    private static Registry reg;
    
    KerdesDeploy(){
        try {
            reg = LocateRegistry.createRegistry(8888);
        } catch (RemoteException ex) {
            System.out.println("Hiba a registry létrehozásakor.");
            ex.printStackTrace();
        }
        
        try {
            reg.rebind("kerdesek", new KerdesGyujtemeny());
        } catch (RemoteException ex) {}
    }
    
    public static void main (String[] args){
        new KerdesDeploy();
    }
}

