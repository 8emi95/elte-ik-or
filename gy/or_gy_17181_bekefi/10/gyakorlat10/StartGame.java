package gyakorlat10;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;


public class StartGame {
    
    public static void main(String[] args) throws InterruptedException {
        Thread t = new Thread() {
            @Override
            public void run() {
                try {
                    Server.main(null);
                } catch (IOException ex) {
                    Logger.getLogger(StartGame.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        };
        t.start();
        
        for (int i=0; i<6; i++) {
            final String name = "Jatekos"+(i+1);
            Thread t1 = new Thread() {
                @Override
                public void run() {
                    try {
                        Client.main(new String[] {name});
                    } catch (IOException ex) {
                        Logger.getLogger(StartGame.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            };
            t1.start();
            Thread.sleep(1000);
        }
    }
}
