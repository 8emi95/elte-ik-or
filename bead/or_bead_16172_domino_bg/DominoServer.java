package domino;

import java.net.*;
import java.io.*;
import java.util.*;

public class DominoServer {

    private final LinkedList<Domino> dominos;
    private final byte playerCount;
    private final String outFilename;
    private final ArrayList<Client> clients;

    // a legutóbbi megkapott dominó érték
    private String lastReceivedValue;

    // eddig hányszor nem tudott a szerver egymás után
    // dominót adni
    private byte fails = 0;

    // vége-e a játéknak
    private boolean gameEnded = false;

    public DominoServer(LinkedList<Domino> dominos, byte playerCount, String outFilename) {
        this.dominos = dominos;
        this.playerCount = playerCount;
        this.outFilename = outFilename;
        this.clients = new ArrayList<Client>();
    }

    public DominoServer(String dominoFilename, byte playerCount, String outFilename) throws Exception {
        LinkedList<Domino> dominos = new LinkedList<>();
        Scanner dominoInput = new Scanner(new File(dominoFilename));
        while (dominoInput.hasNextLine()) {
            String line = dominoInput.nextLine();
            Domino domino = new Domino(line);
            dominos.add(domino);
        }
        dominoInput.close();

        this.dominos = dominos;
        this.playerCount = playerCount;
        this.outFilename = outFilename;
        this.clients = new ArrayList<Client>();
    }

    public void startServer() throws Exception {
        ServerSocket server = new ServerSocket(60504);

        // kliensek csatlakozása
        for (byte cliId = 0; cliId < playerCount; ++cliId) {
            System.out.println("[SERVER] Kliensre varakozas...");
            Socket clientSock = server.accept();
            clientSock.setTcpNoDelay(true);
            clients.add(new Client(cliId, clientSock));
        }

        System.out.println("[SERVER] Osszes kliens megjott, dominok kikuldese...");

        // kezdeti dominók elküldése
        for (Client client : clients) {
            for (byte i = 0; i < 7; ++i) {
                Domino domino = dominos.pollFirst();
                client.write(domino.toString());
            }
        }

        // nevek fogadása
        for (Client client : clients) {
            System.out.println("[SERVER] Varakozas kliens nevere...");
            client.playerName = client.read();
            System.out.println("[SERVER] " + client.toString() + " OK");
        }

        // START elküldése
        Client first = clients.get(0);
        first.write("START");
        Client clientOnTurn = first;

        // fájl a bejövő üzenetek naplózására
        PrintWriter toFile = new PrintWriter(new FileOutputStream(outFilename));

        // üzenetek figyelése
        while (!gameEnded) {
            System.out.println("[SERVER] Varakozas uzenetre klienstol - " + clientOnTurn.toString());

            String message = clientOnTurn.read();
            Client nextClient = getNextClient(clientOnTurn);
            toFile.println(clientOnTurn.playerName + ": " + message);

            if (message.equals("NYERTEM")) {
                clientWon(clientOnTurn);
            } else if (message.equals("UJ")) {
                dominoRequested(clientOnTurn, nextClient);
            } else {
                dominoPlaced(clientOnTurn, nextClient, message);
            }

            clientOnTurn = nextClient;
        }

        System.out.println("[SERVER] Jatek vege");

        // ha azért lett vége, mert senkinek nem tudtunk adni,
        // kiírjuk a naplóba, hogy döntetlen
        if (fails == playerCount) {
            System.out.println("[SERVER] Dontetlen");
            toFile.println("DONTETLEN");
        }

        // kliensek bezárása
        for (Client client : clients) {
            client.close();
        }

        toFile.close();
        server.close();
    }

    private Client getNextClient(Client current) throws Exception {
        if (current.id == playerCount - 1) {
            return clients.get(0);
        } else {
            return clients.get(current.id + 1);
        }
    }

    private void clientWon(Client winner) throws Exception {
        System.out.println("[SERVER] " + winner.toString() + " nyert");

        for (Client client : clients) {
            if (client.id != winner.id) {
                client.write("VEGE");
            }
        }

        gameEnded = true;
    }

    private void dominoRequested(Client clientOnTurn, Client nextClient) throws Exception {
        System.out.println("[SERVER] " + clientOnTurn.toString() + " dominot ker");

        // dominó küldése
        Domino domino = dominos.pollFirst();
        if (domino == null) {
            // nem tudunk dominót adni
            ++fails;

            if (fails == playerCount) {
                // senkinek nem tudtunk dominót adni
                // döntetlen
                for (Client client : clients) {
                    client.write("VEGE");
                }

                gameEnded = true;
                return;
            } else {
                clientOnTurn.write("NINCS");
            }
        } else {
            // van dominó, amit adhatunk
            fails = 0;
            clientOnTurn.write(domino.toString());
        }

        // a következő kliensnek elküldjük a legutóbb
        // lerakott értéket
        nextClient.write(lastReceivedValue);
    }

    private void dominoPlaced(Client clientOnTurn, Client nextClient, String value) throws Exception {
        System.out.println("[SERVER] " + clientOnTurn.toString() + " dominot rak");

        lastReceivedValue = value;
        fails = 0;
        nextClient.write(value);
    }

    public static void main(String[] args) throws Exception {
        int playerCount = Integer.parseInt(args[0]);
        
        if (playerCount < 2 || playerCount > 4) {
            System.out.println("Nem megfelelo a jatekosok szama.");
            playerCount = 2;
        }

        try {
            DominoServer dominoServer = new DominoServer(args[1], (byte) playerCount, args[2]);
            dominoServer.startServer();
        } catch (Exception e) {
            System.out.println("[SERVER] Szerver hiba:");
            System.out.println(e.toString());
            throw e;
        }
    }

}

class Client {

    public final byte id;
    public String playerName;

    private Socket socket;
    private PrintWriter toClient;
    private BufferedReader fromClient;

    public Client(byte id, Socket socket) throws Exception {
        this.id = id;
        this.socket = socket;
        fromClient = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        toClient = new PrintWriter(socket.getOutputStream(), true);
    }

    public String read() throws Exception {
        return fromClient.readLine();
    }

    public void write(String data) throws Exception {
        toClient.println(data);
    }

    public void close() throws Exception {
        toClient.close();
        fromClient.close();
        socket.close();
    }

    @Override
    public String toString() {
        return playerName + " (" + id + ")";
    }

}
