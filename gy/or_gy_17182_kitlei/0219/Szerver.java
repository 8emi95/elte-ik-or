
import java.util.*;
import java.net.*;
import java.io.*;

public class Szerver {
	public static void main(String[] args) throws Exception {
		final int PORT = 12345;
		// final int PORT = 65535;

		try (
			ServerSocket ss = new ServerSocket(PORT);
			Socket s        = ss.accept();
			Scanner sc      = new Scanner(s.getInputStream(), "utf-8");
			PrintWriter pw  = new PrintWriter(s.getOutputStream());
		) {
			String be = sc.nextLine();
			System.out.println(be);
			pw.println(be);
			pw.flush();
		}
	}
}
