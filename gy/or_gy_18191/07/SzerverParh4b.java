import java.io.PrintWriter;
import java.net.ServerSocket;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;

public class SzerverParh4b {
	public static void main(String[] args) throws Exception {
		Map<String, ClientInfo> nameToClient = new ConcurrentHashMap<>();
//		Map<String, ClientInfo> nameToClient = new HashMap<>();

		try (
			ServerSocket ss = new ServerSocket(12345);
		) {
			while (true) {
				ClientInfo client = new ClientInfo(ss);
				Thread thread = new Thread(() -> {
					// Java 9+
					try (client) {
						String name = client.sc.nextLine();
						nameToClient.put(name, client);
						while (client.sc.hasNextLine()) {
							String msg = client.sc.nextLine();
							for (Map.Entry<String, ClientInfo> entry : nameToClient.entrySet()) {
								PrintWriter pw2 = entry.getValue().pw;
								pw2.println(msg);
								pw2.flush();
							}
						}
						nameToClient.remove(name);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					// Java 8-
//					client.close();
				});

				thread.start();
			}
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
