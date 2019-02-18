package verseny;

import java.io.*;
import java.net.*;
import java.util.*;

// public class ClientInfo implements AutoCloseable {
public class ClientInfo {
    public String name;
    public Racer animal1;
    public Racer animal2;

    private Socket s;
    private Scanner sc;
    private PrintWriter pw;

    public ClientInfo(ServerSocket ss) throws Exception {
        this.s = ss.accept();
        this.sc = new Scanner(s.getInputStream(), "utf-8");
        this.pw = new PrintWriter(s.getOutputStream());
    }

    public String read() throws Exception {
        return sc.nextLine();
    }

    public void write(String data) throws Exception {
        pw.println(data);
    }

    public void close() throws Exception {
        System.out.println("Client " + name + " disconnected.");
        // System.out.println("Client disconnected.");
        pw.close();
        sc.close();
        s.close();
    }

    // @Override
    // public void close() throws Exception {
    // 	if (s != null) {
    // 		s.close();
    // 	}
    // }
}