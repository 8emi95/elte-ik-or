
package rmi;

import java.rmi.*;


public interface KerdesGyujtemenyI extends Remote{
    void feltolt(String file) throws RemoteException;
    void ujKerdesValasz(String kerdes, int valasz) throws RemoteException;
    String kovetkezoKerdesValasz() throws RemoteException;
}
