
import java.util.*;
import java.net.*;
import java.io.*;

public class Kliens {
	public static void main(String[] args) throws Exception {
		// final String GEP = "localhost";
		final String GEP = "127.0.0.1";
		final int PORT = 12345;
		// final int PORT = 65535;

		try (
			Socket s        = new Socket(GEP, PORT);
			Scanner sc      = new Scanner(s.getInputStream(), "utf-8");
			PrintWriter pw  = new PrintWriter(s.getOutputStream());

			Scanner scIn    = new Scanner(System.in);
		) {
			int hanyszor = scIn.nextInt();

			pw.println(hanyszor);
			pw.flush();

			String valasz = sc.nextLine();
			System.out.println(valasz);
		}
	}
}
