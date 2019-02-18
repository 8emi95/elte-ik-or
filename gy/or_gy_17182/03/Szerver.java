// F3
// kliens: fájlból egész számokat olvas, szervernek küldi sorban
// szerver: (mondjuk n ↦ 2*n+1) fv minden számra, eredményt visszaküldi
// kliens: eredményeket egy fájlba írja ki sorban. Ha a 0 szám következne a kliensoldalon, akkor a kliens kilép.
// a) A kliens most küldje át az összes adatot a szervernek, és csak utána fogadja a visszaérkező számokat; hasonlóan, a szerver fogadja az összes számot, és csak utána küldje el őket átalakítva a kliensnek.
// b) A szerver várakozzon a kliens kilépése után új kliensre, és ez ismétlődjön a végtelenségig.

import java.util.*;
import java.net.*;
import java.io.*;

public class Szerver {
    public static void main(String[] args) throws Exception {
        final int PORT = 12345;

        try (
            ServerSocket ss = new ServerSocket(PORT);
            Socket s = ss.accept();
            Scanner sc = new Scanner(s.getInputStream(), "utf-8");
            PrintWriter pw = new PrintWriter(s.getOutputStream());
        ) {
            // Kitlei meo (egyszerre küldi)
            List<Integer> nums = new ArrayList<>();
            while (sc.hasNextInt()) {
                int num = sc.nextInt();
                if (num == 0) {
                    break;
                }
                nums.add(num);
            }
            for (int num : nums) {
                int result = f(num);
                pw.println(result);
                pw.flush();
            }
        }
    }

    private static int f(int n) {
        return 2 * n + 1;
    }
}