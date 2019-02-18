package gyak4;

public class Szalak {

    public static void main(String[] args) {
        int n = 5 /*Integer.parseInt(args[0])*/;
        for (int i=1; i<=n; i++) {
            new KiiroSzal(i,n).start();
        }    
    }
}

class KiiroSzal extends Thread {
    private int sorszam;
    private int n;
   
    public KiiroSzal(int sorszam, int n) {
        this.sorszam = sorszam;
        this.n = n;
    }
 
    @Override
    public void run() {
        for (int i=0; i<(n-sorszam+1); i++) {
            try {
                Thread.sleep(sorszam*1000);
            } catch (InterruptedException e) {}    
            System.out.println(this.getName());  
        }
    }
}
