import java.net.*;;
import java.util.*;;
import java.io.*;

public class Kliens
{
    public static void main(String[] args) throws Exception {
        // String MACHINE = "www.inf.elte.hu";  // port 80 - ha megfelelő üzenetet küldök,
                                                // akkor azt hiszi böngésző vagyok és visszaküldi amit a böngészőnek küldene
        String MACHINE = "localhost"; // "127.0.0.1", egyéb név, egyéb ip cím
        int PORT = 12345; // egyeznie kell a szerver portjával

        try ( // duplex kapcsolat, küldés hallgatás egyszerre
            Socket s = new Socket(MACHINE, PORT);
            Scanner sc = new Scanner(s.getInputStream(), "UTF-8");
            PrintWriter pw = new PrintWriter(s.getOutputStream());
        )
        {
            pw.println(12345);
            pw.flush();

            // csak azt tudjuk h számot küld a szerver
            int result = sc.nextInt();
            System.out.println(result);
        }
        // ss.close();
    }
}