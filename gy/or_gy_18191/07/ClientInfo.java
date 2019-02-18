import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class ClientInfo implements AutoCloseable {
	Socket s;
	Scanner sc;
	PrintWriter pw;

	public ClientInfo(ServerSocket ss) throws IOException {
		this.s = ss.accept();
		this.sc = new Scanner(s.getInputStream());
		this.pw = new PrintWriter(s.getOutputStream());
	}

	@Override
	public void close() throws Exception {
		if (s != null) {
			s.close();
		}
	}

	public boolean sendMessageTo(ClientInfo otherClient) {
		if (!sc.hasNextLine())   return false;

		String msg = sc.nextLine();
		otherClient.pw.println(msg);
		otherClient.pw.flush();

		return true;
	}
}
