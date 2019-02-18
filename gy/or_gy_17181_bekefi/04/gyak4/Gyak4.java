package gyak4;

import java.util.ArrayList;
import java.util.List;

public class Gyak4 {
    
    public static void main(String[] args) throws InterruptedException {
        List<Thread> szalak = new ArrayList<>();
        for (int i=0; i<5; i++) {
            Thread t = new Thread() {
                @Override
                public void run() {
                    System.out.println("abcd: "+this.getName());
                }
            };
            szalak.add(t);
            t.start();
        }
        
        for (Thread t : szalak) {
            t.join();
        }
        System.out.println("A program lefutott");
    }
    
}
