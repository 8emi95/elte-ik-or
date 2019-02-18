package gyak4;

public class Szalak2 {
    
    public static void main(String[] args) {
        for (int i=0; i<500; i++) {
            Szal sz = new Szal();
            sz.start();
        }
    }
}

class Szal extends Thread {
    private static int n = 100;
    private static Object lock = new Object();
    
    @Override
    public void run() {
        boolean end = false;
        while(!end) {
            synchronized(/*lock*/Szal.class) {
                if (n>0) {
                    n--;
                    System.out.println(n+" "+this.getName());
                }
                else {
                    end = true;
                }
            }
        }
    }
}
