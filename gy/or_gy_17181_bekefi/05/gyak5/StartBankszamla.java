package gyak5;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;


public class StartBankszamla {
    
    public static void main(String[] args) {
        Thread t = new Thread() {
            @Override
            public void run() {
                try {
                    Bankszamla.main(new String[] {"10000", "3"});
                } catch (IOException ex) {
                    Logger.getLogger(StartSC.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InterruptedException ex) {
                    Logger.getLogger(StartBankszamla.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        };
        t.start();
        
        for (int i=0; i<3; i++) {
            Thread t2 = new Thread() {
                @Override
                public void run() {
                    try {
                        BClient.main(null);
                    } catch (IOException ex) {
                        Logger.getLogger(StartSC.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            };
            t2.start();
        }
    }
}
