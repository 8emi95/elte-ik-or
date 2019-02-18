package alapfeladat;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public class Jatek {
    public static void main(String[] args) throws InterruptedException {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    AkasztofaSzerver.main(new String[] {"input.txt"});
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        Thread.sleep(500);

        List<Thread> threads = new ArrayList<>();

        Thread t1 = new Thread() {
            @Override
            public void run() {
                try {
                    AkasztofaKliens.main(new String[] {"Jatekos1", "10"});
                } catch (RemoteException | NotBoundException | InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        threads.add(t1);
        t1.start();
        Thread.sleep(500);


        Thread t2 = new Thread() {
            @Override
            public void run() {
                try {
                    AkasztofaKliens.main(new String[] {"Jatekos2", "7"});
                } catch (RemoteException | NotBoundException | InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        threads.add(t2);
        t2.start();
        Thread.sleep(500);

        Thread t3 = new Thread() {
            @Override
            public void run() {
                try {
                    AkasztofaKliens.main(new String[] {"Jatekos3", "8"});
                } catch (RemoteException | NotBoundException | InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        threads.add(t3);
        t3.start();
        Thread.sleep(500);

        for (Thread t : threads) {
        t.join();
        }

        System.out.println("Veget ert a jatek.");
    }

}
