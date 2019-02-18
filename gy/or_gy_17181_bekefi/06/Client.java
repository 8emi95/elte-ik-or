import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;


public class Client {
    
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Socket s = new Socket("localhost", Server.PORT);
        
        Circle c1 = new Circle(new Point(1, 2), 3);
        ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
        oos.writeObject(c1);
        oos.flush();
        //oos.close();
        System.out.println("Kliens: ezt a kort kuldtem: "+c1.toString());
        
        ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
        Object o = ois.readObject();
        System.out.println("Kliens: ezt a keruletet kaptam: "+o.toString());
        //ois.close();
        
        oos.close();
        ois.close();
        s.close();
    }
}
