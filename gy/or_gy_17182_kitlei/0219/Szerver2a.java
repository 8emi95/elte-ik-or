
import java.util.*;
import java.net.*;
import java.io.*;

public class Szerver2a {
	public static void main(String[] args) throws Exception {
		final String param = args[0];

		final int PORT = 12345;
		// final int PORT = 65535;

		try (
			ServerSocket ss = new ServerSocket(PORT);
			Socket s        = ss.accept();
			Scanner sc      = new Scanner(s.getInputStream(), "utf-8");
			PrintWriter pw  = new PrintWriter(s.getOutputStream());
		) {
			int hanyszor = sc.nextInt();
			// Integer.parseInt(sc.nextLine());

			for (int i = 0; i < hanyszor; ++i) {
				pw.print(param);
			}
			pw.println();
			pw.flush();
		}
	}
}
