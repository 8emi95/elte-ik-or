import java.io.*;

public class Main {
    
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Circle c1 = new Circle(new Point(1, 2), 3);
        Circle c2 = new Circle(new Point(4, 5), 6);
        
        System.out.println(c2.toString());
        System.out.println(c2.getC());
        System.out.println(c2.toString());
        
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("circles.ser"));
        oos.writeObject(c1); // objektumiro cache-eli az adatokat -> ezert nem valtozik meg a move utan a kozeppont, mindig a legelso valtozat irodik ki
        c1.move(1, 1);
        oos.reset(); // elfelejti a cache-elt adatokat
        oos.writeObject(c1);
        oos.writeObject(c2);
        oos.flush();
        oos.close();
        
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream("circles.ser"));
        for (int i=0; i<3; i++) {
            System.out.println(ois.readObject().toString());
        }
        ois.close();
    }
}
