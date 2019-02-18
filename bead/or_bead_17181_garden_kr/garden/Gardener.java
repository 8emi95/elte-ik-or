package garden;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Gardener {
    static final int MAX_FAIL = 3;
    public static void main(String[] args) {

        String name = args[0];
        int wateringFrequency = Integer.parseInt(args[1]);
        new Thread() {
            @Override
            public void run() {
                try {
                    Socket socket = new Socket("localhost", 12107);

                    int failedWateringCounter = 0;
                    ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                    oos.flush();
                    ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());

                    while (failedWateringCounter < MAX_FAIL) {
                        oos.writeObject(ServerCommunicationConstants.WATER_PLANT + name);
                        oos.flush();
                        int numberReceived = ois.readInt();
                        System.out.println(name + ": " + numberReceived);
                        failedWateringCounter = numberReceived == 0 ? failedWateringCounter  + 1 : 0;
                        if (failedWateringCounter == MAX_FAIL) {
                            oos.writeObject(ServerCommunicationConstants.CONNECTION_CLOSE + name);
                            oos.flush();
                            System.out.println(ServerCommunicationConstants.CONNECTION_CLOSE + name);
                        }
                        Thread.sleep(1000*wateringFrequency);
                    }
                    socket.close();
                } catch (InterruptedException | IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
}
