package gyak3;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;


public class StartProgram {
    
    public static void main(String[] args) {
        final int n = 3;
        new Thread() {
            @Override
            public void run() {
                Server.main(new String[] {Integer.toString(n)});
            }
        }.start();
        
        for (int i=0; i<n; i++) {
            new Thread() {
                @Override
                public void run() {
                    try {
                        Client.main(null);
                    } catch (IOException ex) {
                        Logger.getLogger(StartProgram.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }.start();
        }
    }
}
