package garden;

import com.sun.deploy.util.SessionState;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class GardenStore {
    private int numberOfPlantsSelling;
    private String fileName;
    private List<Plant> plants;
    private List<ClientHandler> clients;

    public GardenStore(int numberOfPlantsSelling, String fileName) {
        this.numberOfPlantsSelling = numberOfPlantsSelling;
        this.fileName = fileName;
        plants = new ArrayList<>();
        clients = new ArrayList<>();
    }

    public void run() throws IOException, NotBoundException, PlantWitheredException, InterruptedException {
        ServerSocket server = new ServerSocket(12107);
        System.out.println("Gardenstore set up...");
        createStore();
        createDatabase();
        acceptClients(server);
    }

    private void createDatabase() {
        try {
            Class.forName("org.hsqldb.jdbc.JDBCDriver");
            Connection conn = DriverManager.getConnection("jdbc:hsqldb:file:plants.db");
            System.out.println("Database created.");
            createTable(conn);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

    }

    private void createTable(Connection conn) throws SQLException {
        Statement stat = conn.createStatement();
        stat.executeUpdate("drop table if exists plants;"); //
        String sql = "CREATE TABLE if not exists plants (" +
                "id BIGINT, " +
                "species VARCHAR(80), " +
                "price INTEGER, " +
                "watering_frequency INTEGER, " +
                "time_of_last_watering BIGINT, " +
                "withered BOOLEAN, " +
                "sold BOOLEAN);";
        stat.executeUpdate(sql);
        stat.executeUpdate(sql);
        System.out.println("Table created.");
        plantsToDB(plants, conn);
    }

    private static void plantsToDB(List<Plant> plants, Connection conn) throws SQLException {
        PreparedStatement prep = conn.prepareStatement("insert into plants (id, species, price, watering_frequency, withered, sold) values (?, ?, ?, ?, ?, ?);");
        for (Plant plant : plants) {
            prep.setLong(1, plant.getID());
            prep.setString(2, plant.getSpecies());
            prep.setInt(3, plant.getPrice());
            prep.setInt(4, plant.getWateringFrequencyInSeconds());
            prep.setBoolean(5, false);
            prep.setBoolean(6, false);
            prep.addBatch();
        }
        prep.executeBatch();
    }

    private static synchronized void setLastTwoColumns(String column, int id, Connection connection) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("update plants set "+column+" = true where id = ?;");
        preparedStatement.setInt(1, id);
        preparedStatement.executeUpdate();
    }

    private void acceptClients(ServerSocket server) throws IOException, InterruptedException {
        server.setSoTimeout(ServerCommunicationConstants.TIMEOUT);
        try {
            while (true) {
                ClientHandler c = new ClientHandler(server.accept());
                c.start();
                clients.add(c);
            }
        } catch(SocketTimeoutException ex) {

            for (ClientHandler c : clients) {
                c.join();
            }
        }

    }

    private void createStore() throws RemoteException, NotBoundException, PlantWitheredException {
        Registry reg = LocateRegistry.getRegistry(8888);
        Remote remote = reg.lookup("factory");
        PlantFactoryInterface pi = (PlantFactoryInterface) remote;
        for (int i = 0; i < numberOfPlantsSelling; ++i) {
            plants.add(pi.getPlant());
        }
    }

    public List<Plant> getPlants() {
        return plants;
    }

    public static void main(String[] args) throws NotBoundException, PlantWitheredException, InterruptedException {

        GardenStore gardenStore = new GardenStore(Integer.parseInt(args[0]), args[1]);
        try {
            gardenStore.run();
        } catch (IOException ex) {
            System.err.println("Store couldn't set up.");
        }
    }

    class ClientHandler extends Thread {

        private Socket client;
        private ObjectOutputStream oos;
        private ObjectInputStream ois;

        public ClientHandler(Socket client) {
            this.client = client;
        }

        @Override
        public void run() {
            try {
                oos = new ObjectOutputStream(client.getOutputStream());
                oos.flush();
                ois = new ObjectInputStream(client.getInputStream());
                while(null != client) {
                    Object message = ois.readObject();
                    if (null != message) {
                        handleClient((String)message);
                    }
                }

            } catch(IOException | ClassNotFoundException ex){
                System.err.println("Communication error: " + ex);
            }
        }

        private void handleClient(String message) throws IOException {
            System.out.print(message + ": ");
            if (message.contains(ServerCommunicationConstants.WATER_PLANT)) {
                handleGardener();
            } else if (message.contains(ServerCommunicationConstants.BUY_PREFIX)) {
                handleCustomer();
            } else {
                closeClient();
            }
        }

        private void handleGardener() throws IOException{
            List<Plant> witheredPlants = new ArrayList<>();
            List<Plant> wateredPlants = new ArrayList<>();

            synchronized (ClientHandler.class) {
                for (Plant plant : plants) {
                    try {
                        plant.waterPlant(System.currentTimeMillis());
                        wateredPlants.add(plant);
                    } catch(PlantWitheredException ex) {
                        witheredPlants.add(plant);
                        //setLastTwo
                    }
                }
                plants.removeAll(witheredPlants);
            }
            oos.writeInt(wateredPlants.size());
            oos.flush();
        }

        private void handleCustomer() throws IOException {
            synchronized (ClientHandler.class) {
                Optional<Plant> firstHealthyPlant = plants.stream()
                        .filter((Plant p)-> !p.isWithered())
                        .findFirst();

                if (!firstHealthyPlant.isPresent()) {
                    oos.writeObject(ServerCommunicationConstants.NO_PLANTS_LEFT);
                    oos.flush();
                    System.out.println(ServerCommunicationConstants.NO_PLANTS_LEFT);
                } else {
                    oos.writeObject(firstHealthyPlant.get());
                    oos.flush();
                    System.out.println(firstHealthyPlant.get().getID() + " " + firstHealthyPlant.get().getSpecies());
                    plants.remove(firstHealthyPlant.get());
                    //setLastTwo
                }
            }
        }

        private void closeClient() throws IOException {
            client.close();
            client = null;
        }
    }

}