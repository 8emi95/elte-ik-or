import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Kliens4 {
	public static void main(String[] args) throws Exception {
		try (
			Socket s = new Socket("localhost", 12345);
			Scanner sc = new Scanner(s.getInputStream());
			PrintWriter pw = new PrintWriter(s.getOutputStream());
			Scanner scIn = new Scanner(System.in);
		) {
			Thread thread1 = new Thread(() -> {
				copy(sc, new PrintWriter(System.out));
			});

			Thread thread2 = new Thread(() -> {
				copy(scIn, pw);
			});

			thread1.start();
			thread2.start();

//			thread1.join();
//			thread2.join();

			System.out.println("Finished.");
		}
	}

	private static void copy(Scanner scIn, PrintWriter pw) {
		while (scIn.hasNextLine()) {
			String msg = scIn.nextLine();
			pw.println(msg);
			pw.flush();
		}
	}
}
