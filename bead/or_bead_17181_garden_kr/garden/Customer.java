package garden;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Customer {

    static final int MAX_FAIL = 3;

    public static void main(String[] args) {
        String name = args[0];
        int shoppingFrequency = Integer.parseInt(args[1]);
        int totalDesiredPlants = Integer.parseInt(args[2]);

        new Thread() {
            @Override
            public void run() {
                try {
                    Socket socket = new Socket("localhost", 12107) ;
                    ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                    oos.flush();
                    ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                    List<String> speciesBought = new ArrayList<>();
                    List<Integer> speciesBoughtPrices = new ArrayList<>();

                    Object message;
                    int failedShoppingCounter = 0;
                    while (speciesBought.size() < totalDesiredPlants && failedShoppingCounter < MAX_FAIL) {
                        oos.writeObject(ServerCommunicationConstants.BUY_PREFIX + name);
                        oos.flush();
                        message = ois.readObject();
                        if (ServerCommunicationConstants.NO_PLANTS_LEFT.equals(message)) {
                            ++failedShoppingCounter;
                        } else {
                            failedShoppingCounter = 0;
                            Plant boughtPlant = (Plant)message;
                            speciesBought.add(boughtPlant.getSpecies());
                            speciesBoughtPrices.add(boughtPlant.getPrice());
                        }

                        Thread.sleep(1000*shoppingFrequency);
                    }

                    if (failedShoppingCounter == MAX_FAIL) {
                        oos.writeObject(ServerCommunicationConstants.CONNECTION_CLOSE);
                        oos.flush();
                        System.out.println(ServerCommunicationConstants.CONNECTION_CLOSE);
                    } else {
                        oos.writeObject(ServerCommunicationConstants.SHOPPING_OVER + name);
                        oos.flush();
                        System.out.println(ServerCommunicationConstants.SHOPPING_OVER + name);
                    }
                    socket.close();
                } catch (InterruptedException | IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
}
