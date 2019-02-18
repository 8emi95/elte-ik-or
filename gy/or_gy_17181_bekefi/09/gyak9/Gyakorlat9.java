package gyak9;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class Gyakorlat9 {

    
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        //adatbazis letrehozasa
        Class.forName("org.hsqldb.jdbc.JDBCDriver");
        Connection conn = DriverManager.getConnection("jdbc:hsqldb:file:people.db"); // ha nincs ilyen nevu adatbazis, letrehozza
        System.out.println("adatbazis letrehozva");
        
        //tabla letrehozasa
        Statement stat = conn.createStatement(); // utasitast futtato objektum
        
        // modosito, illetve lekerdezo tipusok vannak
        // modosito nem ad vissza eredmenyt, lekerdezo igen, modositot executeUpdate-tel futtatni
        
        stat.executeUpdate("drop table if exists people;"); // ha letezett people nevu tabla, eldobja
        String sql = "CREATE TABLE if not exists people (" + // hozza letre, ha nem letezett a people nevu tabla, 2 oszlop: name, occupation
                     "id       INT, " + 
                     "jelszo VARCHAR(80));";
        stat.executeUpdate(sql);
        System.out.println("tabla letrehozva");
        
        //tabla feltolese 1.0
        String sql1 = "INSERT INTO people (id, jelszo) VALUES (1, 'jelszo1');"; 
        String sql2 = "INSERT INTO people (id, jelszo) VALUES (2, 'jelszo2');"; 
        stat.executeUpdate(sql1);
        stat.executeUpdate(sql2);
        
        
        //feltoltes 2.0    
        // string itt is a lefuttatando parancs, de felparameterezheto (kerdojelek a parameterek helye), parameterek sorszamozasa 1-tol indul
        PreparedStatement prep = conn.prepareStatement("insert into people (id, jelszo) values (?, ?);");
        prep.setInt(1, 3);  
        prep.setString(2, "jelszo3");   
        prep.addBatch(); // addBatch() hozzaadja egy koteghez, nem futtatja le
        
        prep.setInt(1, 4);  
        prep.setString(2, "jelszo4");   
        prep.addBatch(); // prep.setTipus(), ahol tipus az oszlop tipusa a tablaban
        prep.executeBatch(); // az osszes utasitast a batch-bol lefuttatja egy tranzakcioban
        
        
        //lekerdezes
        ResultSet rs = stat.executeQuery("select * from people;"); // executeQuery() a lekerdezes futtatasa
        while (rs.next()) { // eredmeny bejarasa, tabla sorait kapjuk vissza, elejen kivul van, next() igaz/hamisat ad, van-e kovetkezo sor, es a mutato ugrik a kovetkezo sorra
            // mar az elso sor beolvasasaert is kell next()
            System.out.println("id: " + rs.getInt("id")+", jelszo: "+rs.getString(2)); // oszlop neve helyett a sorszama is megadhato
        }
        rs.close(); // memoriabol kitorli a result adatait, felszabaditja a helyet
        
        //lezaras
        stat.close();
        conn.close();

    }
    
}
