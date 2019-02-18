package zh20160603.feladatB;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Teszt {
    
    public static void main(String[] args) throws InterruptedException {
        new Thread(() -> {
            try {
                Lottozo.main(new String[] {"50"});
            } catch (RemoteException ex) {
                Logger.getLogger(Teszt.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(Teszt.class.getName()).log(Level.SEVERE, null, ex);
            }
        }).start();
        Thread.sleep(1000);
        new Thread(() -> {
            try {
                Sorsolo.main(null);
            } catch (RemoteException ex) {
                Logger.getLogger(Teszt.class.getName()).log(Level.SEVERE, null, ex);
            } catch (NotBoundException ex) {
                Logger.getLogger(Teszt.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InterruptedException ex) {
                Logger.getLogger(Teszt.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(Teszt.class.getName()).log(Level.SEVERE, null, ex);
            }
        }).start();
        Thread.sleep(500);
        for (int i=0; i<6; i++) {
            String nev = "E"+i;
            int penz = i*10;
            String fileNev = (i%2 == 0) ? "szamok1.txt" : "szamok2.txt";
            new Thread(() -> {
                try {
                    Ember.main(new String[] {nev, Integer.toString(penz), fileNev});
                } catch (Exception ex) {
                    Logger.getLogger(Teszt.class.getName()).log(Level.SEVERE, null, ex);
                }
            }).start();
            Thread.sleep(300);
        }
    }
}
