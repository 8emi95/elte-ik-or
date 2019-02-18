package gyak5;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class StartSC {
    
    public static void main(String[] args) {
        Thread t = new Thread() {
            @Override
            public void run() {
                try {
                    Server.main(null);
                } catch (IOException ex) {
                    Logger.getLogger(StartSC.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        };
        t.start();
        
        for (int i=0; i<6; i++) {
            Thread t2 = new Thread() {
                @Override
                public void run() {
                    try {
                        Client.main(null);
                    } catch (IOException ex) {
                        Logger.getLogger(StartSC.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            };
            t2.start();
        }
    }
}
