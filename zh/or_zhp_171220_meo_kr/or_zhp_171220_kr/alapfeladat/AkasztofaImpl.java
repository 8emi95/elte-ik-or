package alapfeladat;

import java.io.File;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

public class AkasztofaImpl extends UnicastRemoteObject implements AkasztofaInterface {

    private String word;
    private final String fileName;

    public AkasztofaImpl(String fileName) throws RemoteException {
        this.fileName = fileName;
    }


    @Override
    public synchronized String jatszik(String nev, char betu) throws RemoteException {
        parseFile(fileName);
        StringBuilder sb = new StringBuilder(word.length());
        boolean match = false;
        String response;
        for (char c : word.toCharArray()) {
            if (betu == c) {
                match = true;
                sb.append(c);
            } else {
                sb.append("?");
            }
        }
        response = match ? "TALALAT" : "NEM_TALALT";

        System.out.printf("Szerver: %s %c %s %s\n", nev, betu, response, sb.toString());
        return String.format("%s %s", response, sb.toString());
    }

    private void parseFile(String fileName) {
        try {
            Scanner sc = new Scanner(new File(fileName));
            word = sc.nextLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*


A szerver minden alkalommal írja ki a játékos nevét, a kapott betűt,
a szerver által küldött választ és a szó aktuális állapotát szóközökkel elválasztva a következő alakban:
Szerver: <Játékos> <Betű> <Válasz> <Szó>
Ügyelj a párhuzamos hozzáférésből eredő hibákra!
Pl.:

Szerver: Jatekos1 j NEM_TALALT ???????????
Szerver: Jatekos1 r TALALAT ??r????????
Szerver: Jatekos2 f TALALAT ??r??????f?
Szerver: Jatekos1 y TALALAT ??r?????yf?
Szerver: Jatekos2 g NEM_TALALT ??r?????yf?
Szerver: Jatekos2 n TALALAT ??r????nyf?
Szerver: Jatekos1 a TALALAT ?ara???nyfa
...
Szerver: Jatekos1 c TALALAT karacsonyfa
 */
}
