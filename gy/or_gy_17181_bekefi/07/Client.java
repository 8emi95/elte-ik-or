import java.rmi.*;
import java.rmi.registry.*;

public class Client {
    
    public static void main(String[] args) throws RemoteException, NotBoundException {
        Registry reg = LocateRegistry.getRegistry(); // ha nem adunk meg parametert, akkor localhost 1099, illetve ha valamelyiket adjuk csak meg, akkor is ezezk kozul a masik erteken keresi
        
        Remote stub = reg.lookup("myserver"); // Remote tipusu objektumot ad vissza, szervercsonkot ad, csak az rmi fv-ek
        CircleRemote cr = (CircleRemote)stub;
        
        Circle circ = new Circle(new Point(1, 2), 3);
        double kerulet = cr.getC(circ);
        System.out.println("A "+circ+" kor kerulete: "+kerulet);
        
             
        ProcessString ps = (ProcessString)stub;
        String s = "teszt string";
        System.out.println("Eredeti szoveg: "+s+", megforditva: "+ps.process(s));
    }
}
