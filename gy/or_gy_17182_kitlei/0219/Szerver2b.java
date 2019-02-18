
import java.util.*;
import java.net.*;
import java.io.*;

public class Szerver2b {
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

			// String result = param;

			// for (int i = 1; i < hanyszor; ++i) {
			//	result += param;
			//	// result += result;
			// }

			StringBuilder result = new StringBuilder();

			for (int i = 0; i < hanyszor; ++i) {
				result.append(param);
			}

			pw.println(result.toString());
			pw.flush();
		}
	}
}
