package zh20160603.feladatC;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Lottozo extends UnicastRemoteObject implements LottozoIface {
    private Random rand;
    private final int MAX = 90;
    private final int DB = 5;
    private Set<Integer> nyeroszamok = new HashSet<>();
    private int kassza;
    private Connection conn;
    
    public Lottozo(int kassza) throws RemoteException, ClassNotFoundException, SQLException {
        super();
        rand = new Random(123456);
        this.kassza = kassza;
        Class.forName("org.hsqldb.jdbc.JDBCDriver");
        conn = DriverManager.getConnection("jdbc:hsqldb:file:tippek.db"); // ha nincs ilyen nevu adatbazis, letrehozza
        createTable();
        selectMax();
    }
    
    public static void main(String[] args) throws RemoteException, ClassNotFoundException, SQLException {
        Lottozo lottozo = new Lottozo(Integer.parseInt(args[0]));
        Registry reg = LocateRegistry.createRegistry(8899);
        reg.rebind("lottozo", lottozo);
        System.out.println("A lottozo elindult "+args[0]+" kezdoosszeggel.");
    }
   
    private void selectMax() throws SQLException {
        Statement stat = conn.createStatement();
        ResultSet rs = stat.executeQuery("select nev, max(db) from tippek group by nev, db;");
        while(rs.next()) {
            System.out.println("Legtobb legalabb 2-es tipp: "+rs.getString("nev")+", "+rs.getInt(2));
            break;
        }
    }
    
    private void createTable() throws SQLException {
        Statement stat = conn.createStatement(); // utasitast futtato objektum
        //stat.executeUpdate("drop table if exists tippek;"); // ha letezett people nevu tabla, eldobja
        String sql = "CREATE TABLE if not exists tippek (" + // hozza letre, ha nem letezett a people nevu tabla, 2 oszlop: name, occupation
                     "nev       VARCHAR(80), " + 
                     "db INTEGER);";
        stat.executeUpdate(sql);
        System.out.println("Tabla letrehozva");
    }

    private void updateTable(String nev) throws SQLException {
        Statement stat = conn.createStatement();
        ResultSet rs = stat.executeQuery("select * from tippek where nev = '"+nev+"';");
        if (rs.next()) { // benne van mar a tablaban
            stat.executeUpdate("update tippek set db = db+1 where nev='"+nev+"';");
        }
        else {
            stat.executeUpdate("insert into tippek(nev, db) values ('"+nev+"', 1);");
        }
    }
    
    
    @Override
    public synchronized void sorsol() throws RemoteException {
        if (kassza <= 0) throw new UnsupportedOperationException("Csodbe ment a lottozo."); 
        
        nyeroszamok.clear();
        while(nyeroszamok.size() < DB) {
            nyeroszamok.add(rand.nextInt(MAX)+1);
        }
        System.out.println("L > sorsolas: "+nyeroszamok);
    }

    
    @Override
    public synchronized int jatszik(Set<Integer> szamok, String nev) throws RemoteException {
        if (kassza <= 0) throw new UnsupportedOperationException("Csodbe ment a lottozo."); 
        
        int nyeremeny = -2;
        int talalat = 0;
        for (Integer szam : szamok) {
            if (nyeroszamok.contains(szam)) talalat++;
        }
        if (talalat > 1) {
            nyeremeny += talalat*5;
            try {
                updateTable(nev);
            } catch (SQLException ex) {
                Logger.getLogger(Lottozo.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        kassza -= nyeremeny;
        if (kassza <= 0)  throw new UnsupportedOperationException("Csodbe ment a lottozo."); 
        System.out.println("L > tipp: "+szamok+"; kifizetes: "+nyeremeny+"; új kassza: "+kassza);
        return nyeremeny;
    }
    
}
