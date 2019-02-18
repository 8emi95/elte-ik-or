
package rmi;

import java.io.*;
import java.rmi.*;
import java.rmi.server.*;
import java.util.*;


public class KerdesGyujtemeny extends UnicastRemoteObject implements KerdesGyujtemenyI{
    
    ArrayList kerdes = new ArrayList<String>();
    ArrayList valasz = new ArrayList<Integer>();
    FileReader reader;
    
    
    KerdesGyujtemeny()throws RemoteException{}
    
    
    @Override
    public void feltolt(String file) throws RemoteException{
    
        try{
            reader = new FileReader(file);
        }catch(FileNotFoundException fe){System.err.println("File nem létezik");}
        
        BufferedReader file_br = new BufferedReader(reader);
        String line;
        
        try{
            while (file_br.ready()) {
                 line = file_br.readLine();
                kerdes.add(line);
                line = file_br.readLine();
                valasz.add(Integer.parseInt(line));
            }
            file_br.close();
            reader.close();
        }catch(IOException e){System.err.println("Hiba");}
    }
    
    
    @Override
    public void ujKerdesValasz(String kerdes, int valasz) throws RemoteException{
        this.kerdes.add(kerdes);
        this.valasz.add(valasz);
    }
    
    
    @Override
    public String kovetkezoKerdesValasz() throws RemoteException{
        //visszaadja, és a saját listájából eltávolítja a soron következő kérdés-választ párt egyetlen Stringben összefűzve. A kérdést és a választ egy sortörés választja el egymástól.
        String q = this.kerdes.get(0).toString();
        String a = this.valasz.get(0).toString();
        String kesz = q + "\n" + a;
        this.kerdes.remove(0);
        this.valasz.remove(0);
        return kesz;
    }
}
