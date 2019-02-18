import java.net.*;;
import java.util.*;;
import java.io.*;

public class Szerver
{
    public static void main(String[] args) throws Exception {
        // 1021--65535
        int PORT = 12345;

        try (
            // AutoCloseable
            ServerSocket ss = new ServerSocket(PORT);

            Socket s = ss.accept();
            Scanner sc = new Scanner(s.getInputStream(), "UTF-8");
            PrintWriter pw = new PrintWriter(s.getOutputStream());
        )
        {
            // if (sc.hasNext()) {
            //     String line = sc.nextLine();
            //     pw.println(line);
            //     pw.flush();
            // }

            // ****************************************

            // String text = sc.next(); // whitespace-t átugorja, szót kell találnia
            // pw.println(text);
            // pw.flush();

            // String text2 = sc.next();
            // pw.println(text2);
            // pw.flush();

            // pw.println("end");
            // pw.flush();

            // ****************************************

            // next()-et és nextLine()-t sose használd együtt, csak az egyik legyen
            // String text = sc.next();
            // pw.println(text);
            // pw.flush();

            // String text2 = sc.nextLine();
            // pw.println(text2);
            // pw.flush();

            // ****************************************

            // String text = sc.next();
            // pw.println(text);
            // pw.flush();

            // String text2 = sc.nextLine();
            // String[] parts = text2.split(" ");
            // for (String part : parts) {
            //     pw.println(part);
            //     pw.flush();
            // }

            // ****************************************

            // if (sc.hasNext())
            if (sc.hasNextInt()) {
                int num = sc.nextInt();
                pw.println(num * 2);
                pw.flush(); // enélkül a pw eldöntheti h elküldi-e az üzenetet
            }
        }
        // ss.close();
    }
}