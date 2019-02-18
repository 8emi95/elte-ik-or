package gyak9;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;


public class Server {
    
    public static void main(String[] args) throws IOException, ClassNotFoundException, SQLException {
        ServerSocket server = new ServerSocket(23456);
        Connection conn = createDB();
        createTable(conn);
        insertData(conn);
        while(true) {
            Socket s = server.accept();
            Scanner sc = new Scanner(s.getInputStream());
            PrintWriter pw = new PrintWriter(s.getOutputStream(), true);
            String message = sc.nextLine();
            String[] data = message.split(" ");
            String name = data[0];
            String password = data[1];
            
            boolean found = false;
            ResultSet rs = conn.createStatement().executeQuery("select * from clients;"); 
            while (rs.next()) {
                if (rs.getString(1).equals(name) && rs.getString(2).equals(password)) {
                    found = true;
                }
            }
            rs.close();
            
            
            if (found) {
                pw.println("OK");
            }
            else {
                pw.println("Wrong password");
            }
        }
    }
    
    public static Connection createDB() throws ClassNotFoundException, SQLException {
        Class.forName("org.hsqldb.jdbc.JDBCDriver");
        Connection conn = DriverManager.getConnection("jdbc:hsqldb:file:clients.db"); 
        System.out.println("Adatbazis letrehozva");
        return conn;
    }
    
    public static void createTable(Connection conn) throws SQLException {
        Statement stat = conn.createStatement();
        stat.executeUpdate("drop table if exists clients;");
        String sql = "CREATE TABLE if not exists clients (" + 
                     "name       VARCHAR(80), " + 
                     "pw VARCHAR(80));";
        stat.executeUpdate(sql);
        System.out.println("Tabla letrehozva");
    }
    
    public static void insertData(Connection conn) throws SQLException {
        PreparedStatement prep = conn.prepareStatement("insert into clients (name, pw) values (?, ?);");
        prep.setString(1, "name1");  prep.setString(2, "pw1");   prep.addBatch();
        prep.setString(1, "name2");  prep.setString(2, "pw2");   prep.addBatch();
        prep.setString(1, "name3");  prep.setString(2, "pw3");   prep.addBatch();
        prep.setString(1, "name4");  prep.setString(2, "pw4");   prep.addBatch();
        prep.executeBatch(); 
    }
}
