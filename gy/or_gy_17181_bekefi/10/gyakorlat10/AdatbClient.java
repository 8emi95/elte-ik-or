package gyakorlat10;

import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class AdatbClient {
    
    private static Connection conn;
    
    public static void main(String[] args) throws SQLException, RemoteException, NotBoundException, ClassNotFoundException {
        createDB();
        Statement stat = conn.createStatement();
        Registry reg = LocateRegistry.getRegistry(12345);
        Remote stub = reg.lookup("adatb");
        AdatbInterface ai = (AdatbInterface) stub;

        for (int i=0; i<15; i++) {
            int number = ai.nextNumber();
            stat.executeUpdate("UPDATE SZAMOK SET DB = DB+1 WHERE SZAM = "+number);
        }   
                
        System.out.println("A tabla tartalma torles elott:");
        printDB();
        
        stat.executeUpdate("DELETE FROM SZAMOK WHERE DB = 0");
        System.out.println("Torles utan: ");
        printDB();

    }
    
    public static void createDB() throws ClassNotFoundException, SQLException {
        Class.forName("org.hsqldb.jdbc.JDBCDriver");
        conn = DriverManager.getConnection("jdbc:hsqldb:file:kisbeadando8.db"); 
        System.out.println("Adatbazis letrehozva.");
        
        Statement stat = conn.createStatement(); 

        stat.executeUpdate("drop table if exists szamok;"); 
        String sql = "CREATE TABLE if not exists szamok (" + 
                     "szam       INT NOT NULL, " + 
                     "db INT);";
        stat.executeUpdate(sql);
        System.out.println("Tabla letrehozva.");

        PreparedStatement prep = conn.prepareStatement("insert into szamok (szam, db) values (?, ?);");
        for (int i=0; i<10; i++) {
            prep.setInt(1, (i+1));
            prep.setInt(2, 0);
            prep.addBatch();
        }
        prep.executeBatch(); 
        System.out.println("Tabla feltoltve, a tabla tartalma:");
        
        printDB();     
    }
    
    public static void printDB() throws SQLException {
        Statement stat = conn.createStatement();
        ResultSet rs = stat.executeQuery("select * from szamok;"); 
        while (rs.next()) { 
            System.out.println("szam = " + rs.getInt("szam")+", db = "+rs.getInt("db")); 
        }
        rs.close();
    }
}
