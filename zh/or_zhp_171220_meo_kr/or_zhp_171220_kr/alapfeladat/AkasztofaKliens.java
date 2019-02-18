package alapfeladat;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AkasztofaKliens {
    private final String name;
    private final int maxNumberOfGuesses;
    private final AkasztofaInterface akasztofaInterface;
    private final Random rnd;
    private List<Character> guesses;
    private char[] alphabet = "abcdefghijklmnopqrstuvwxyz".toCharArray();
    private static final long SEED = 55555;
    private static final int WAIT_TIME = 1000;

    public AkasztofaKliens(String name, int maxNumberOfGuesses) throws RemoteException, NotBoundException {
        this.name = name;
        this.maxNumberOfGuesses = maxNumberOfGuesses;
        Registry reg = LocateRegistry.getRegistry(9999);
        akasztofaInterface = (AkasztofaInterface)reg.lookup("akasztofa");
        rnd = new Random(SEED);
        guesses = new ArrayList<>();
    }

    public void playTheGame() throws RemoteException, InterruptedException {
        int numberOfGuesses = 0;
        int numberOfWrongGuesses = 0;
        String messageFromServer;
        String result;
        char guess;
        do {
            guess = alphabet[rnd.nextInt(26)];
            while (guesses.contains(guess)) {
                guess = alphabet[rnd.nextInt(26)];
            }
            guesses.add(guess);
            ++numberOfGuesses;
            messageFromServer = akasztofaInterface.jatszik(name, guess);

            if (messageFromServer.contains("NEM_TALALT")) {
              ++numberOfWrongGuesses;
            }
            System.out.printf("%s: %c %s %d", name, guess, messageFromServer, numberOfWrongGuesses);
            Thread.sleep(WAIT_TIME);
        } while((numberOfGuesses < maxNumberOfGuesses) && (messageFromServer.indexOf('?') >= 0));

        result = (messageFromServer.indexOf('?') >= 0) ? "vesztett" : "nyert";
        System.out.printf("%s %s.", name, result);
    }

    public static void main(String[] args) throws RemoteException, NotBoundException, InterruptedException {
        AkasztofaKliens akasztofaKliens = new AkasztofaKliens(args[0], Integer.parseInt(args[1]));
        akasztofaKliens.playTheGame();
    }
}

/*


guesses clear?

Készíts egy Jatek osztályt, ami elindít egy szervert és három klienst külön szálon.
Minden szál elindítása után várjon fél mp-et. A szerver paramétere input.txt legyen, a játékosoké pedig:

Jatekos1 és 10
Jatekos2 és 7
Jatekos3 és 8
Várd meg, amíg a játékosokat futtató szálak befejeződnek (a szerverét nem kell megvárni), majd írd ki azt, hogy Véget ért a játék..
 */