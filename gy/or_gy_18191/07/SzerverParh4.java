import java.net.ServerSocket;

public class SzerverParh4 {
	public static void main(String[] args) throws Exception {
		try (
			ServerSocket ss = new ServerSocket(12345);

			ClientInfo client1 = new ClientInfo(ss);
			ClientInfo client2 = new ClientInfo(ss);
		) {
			Thread thread1 = new Thread(() -> {
				while (true) {
					boolean isSent = client1.sendMessageTo(client2);
					if (!isSent)   break;
				}
			});

			Thread thread2 = new Thread(() -> {
				while (true) {
					boolean isSent = client2.sendMessageTo(client1);
					if (!isSent)   break;
				}
			});

			thread1.start();
			thread2.start();

//			thread1.join();
//			thread2.join();

			System.out.println("Finished.");
		}
	}
}
