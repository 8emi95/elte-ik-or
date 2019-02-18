import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;


public class StartSockets {
    
    public static void main(String[] args) throws InterruptedException {
        new Thread() {
            
            @Override
            public void run() {
                try {
                    Server.main(new String[] {});
                } catch (IOException ex) {
                    Logger.getLogger(StartSockets.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(StartSockets.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InterruptedException ex) {
                    Logger.getLogger(StartSockets.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
        }.start();
        
        for (int i=0; i<2; i++) {
            Thread.sleep(1000);
            new Thread() {
            
            @Override
            public void run() {
                try {
                    Client.main(new String[] {});
                } catch (IOException ex) {
                    Logger.getLogger(StartSockets.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(StartSockets.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
        }.start();
        }
    }
}
