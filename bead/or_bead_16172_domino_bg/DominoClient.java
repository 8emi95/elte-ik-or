package domino;

import java.net.*;
import java.io.*;
import java.util.*;

public class DominoClient {

    private final String playerName;
    private final LinkedList<Domino> dominos;
    private boolean gameEnded = false;

    public DominoClient(String playerName) {
        this.playerName = playerName;
        dominos = new LinkedList<>();
    }

    public void connect() throws Exception {
        Socket socket = new Socket("localhost", 60504);
        PrintWriter toServer = new PrintWriter(socket.getOutputStream(), true);
        BufferedReader fromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        System.out.println("[CLIENT] " + playerName + " csatlakozva a szerverhez");

        // kezdeti dominók
        for (int i = 0; i < 7; ++i) {
            Domino domino = new Domino(fromServer.readLine());
            addDomino(domino);
        }

        // név elküldése
        toServer.println(playerName);

        // fájl a kommunikáció naplózására
        PrintWriter toFile = new PrintWriter(new FileOutputStream(playerName + ".txt"));

        // szervertől kapott üzenetek figyelése
        while (!gameEnded) {
            String message = fromServer.readLine();
            toFile.print(playerName + ": " + message);

            String response = null;

            if (message.equals("START")) {
                response = start();
            } else if (message.equals("VEGE")) {
                end();
            } else if (message.equals("NINCS")) {
                outOfDominos();
            } else if (message.contains(" ")) {
                Domino domino = new Domino(message);
                addDomino(domino);
            } else {
                response = playerTurn(Byte.parseByte(message));
            }

            if (response != null) {
                toServer.println(response);
                toFile.print(" " + response);
            }

            toFile.println();
        }

        System.out.println("[CLIENT] Jatek vege");

        toServer.close();
        fromServer.close();
        socket.close();
        toFile.close();
    }

    private void addDomino(Domino domino) {
        System.out.println("[CLIENT] Uj domino: " + domino.toString());
        dominos.add(domino);
    }

    private String start() {
        System.out.println("[CLIENT] Kezd");
        Domino firstDomino = dominos.pollFirst();
        return Byte.toString(firstDomino.getValue1());
    }

    private void end() {
        System.out.println("[CLIENT] Vesztett");
        gameEnded = true;
    }

    private void outOfDominos() {
        System.out.println("[CLIENT] Szervernek nincs tobb dominoja");
    }

    private String playerTurn(byte value) {
        if (dominos.size() == 0) {
            gameEnded = true;
            return "NYERTEM";
        }

        // dominó keresése
        ListIterator<Domino> iter = dominos.listIterator();
        while (iter.hasNext()) {
            Domino domino = iter.next();
            byte send = (byte) 127;

            if (domino.getValue1() == value) {
                send = domino.getValue2();
            } else if (domino.getValue2() == value) {
                send = domino.getValue1();
            }

            // ha van megfelelő dominó, akkor töröljük
            // és elküldjük a másik számot
            if (send < 127) {
                iter.remove();
                return Byte.toString(send);
            }
        }

        // ha nem találtunk dominót, kérünk egyet
        return "UJ";
    }

    public static void main(String[] args) {
        DominoClient client = new DominoClient(args[0]);

        try {
            client.connect();
        } catch (Exception e) {
            System.out.println("[CLIENT] Hiba:");
            System.out.println(e.toString());
        }
    }

}
