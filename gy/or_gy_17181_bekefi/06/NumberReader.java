import java.io.*;

public class NumberReader {
    
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream("numbers.ser"));
        
        int intCount = 0;
        int doubleCount = 0;
        int intSum = 0;
        double doubleSum = 0;
        
        for (int i=0; i<100; i++) {
            Object o = ois.readObject();
            if (o instanceof Integer) {
                intCount++;
                intSum += (int)o;
            }
            else if (o instanceof Double) {
                doubleCount++;
                doubleSum += (double)o;
            }
        }
        ois.close();
        
        System.out.println(intCount+" db egesz, osszeguk: "+intSum);
        System.out.println(doubleCount+" double, osszeguk: "+doubleSum);
    }
}
