// Bemelegítő feladatok 2.
// Az első parancssori paraméter egy szöveg, a többi paraméterpár pedig egy-egy karaktert tartalmaz. Cseréld le a szövegben a párok első felében szereplő karaktereket a másodikakra.

import java.util.Scanner;
// import java.io.File;
import java.util.List;
import java.util.AbstractMap.SimpleEntry;

class Swapper {
    Map<String, String> swaps = new HashMap<>();

    makeSwapper(String[] args, int idx) {
        if (args.length == idx + 1) {
            String fileName = args.length[idx];
            makeSwapperFromFile(fileName);
        } else {
            makeSwapperFromFile(args, idx);
        }
    }

    makeSwapperFromArgs(String[] args, int idx) {
        for (int i = 0; i < args.length; i += 2) {
            String from = args[idx + i];
            String to = args[id + i + 1];
            // store
            swaps.put(from, to);
        }
    }
}

public class TextSwapper {
    public static void main(String args[]) {
        // try (
        //     Scanner text = new Scanner(new File(args[0]));
        //     Scanner pairs = new Scanner(new File(args[1]));
        //     PrintWriter pw = new PrintWriter("out.txt");
        // ) {
        //     while (pairs.hasNext()) {
        //         String[] splitPairs = pairs.nextLine().split(";");
        //     }

        //     while (text.hasNext()) {
        //         String splitText = text.nextLine().split(" ");
        //         for (String s : splitText) {
        //             //
        //         }
        //     }
        // }


        // paraméterek beolvasása/értelmezése
        // paraméterek helyességének ellenőrzése, speciális esetek kezelése
        if (args.length < 2) {
            System.err.println("error");
            // System.exit(); // NE - mindent eldob, hibakóddal odaadva jó
            return;
        }
        if (args.length % 2 != 0) {
            System.err.println("error odd");
            return;
        }

        String inputFile = args[0];
        String outputFile = args[1];
        // nem ok, mert nem sorrendtartó
        // Map<String, String> swaps = getSwaps();
        // "Pair" nincs: List<Pair<String, String>> csere...?
        // List<AbstractMap.SimpleEntry<String, String>> swaps = getSwaps();
        Swapper swapper = Swapper.makeSwapper(args, 2);

        // v1
        // szöveg = fájlból olvasás
        // karakter lecserélése aszsövegben
        // eredmény szöveg kiírása a fájlba

        // v2
        // online
        try (
            Scanner scIn = new Scanner(inputFile);
            PrintWriter pwOut = new PrintWriter(outputFile);
        ) {
            // soronként:
            while (scIn.hasNextLine()) {
                // szöveg = fájlból olvasás
                String line = scIn.nextLine();
                // karakter lecserélése aszsövegben
                line = characterSwap(line, swapper);
                // eredmény szöveg kiírása a fájlba
                write(pwOut, line);
            }
        }
    }

    public List<AbstractMap.SimpleEntry<String, String>> getSwaps() {
        //
    }

    private String characterSwap(String line, Swapper swap) {
        String retval = line;
        for (Swapper.Swap swap : swapper.getSwaps()) {
            retval = swap.apply(retval);
        }
        return retval;
    }
}