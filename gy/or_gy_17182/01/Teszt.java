import java.util.Scanner;
import java.io.PrintWriter;
import java.io.File;

class Teszt {
    public static void main(String[] args) throws Exception {
        try (
            // Scanner sc = new Scanner(new File("myfile.txt"));
            // PrintWriter pw = new PrintWriter("out.txt");

            Scanner sc2 = new Scanner(new File("myfile2.txt"));
            PrintWriter pw2 = new PrintWriter("out2.txt");
        ) {
            // while (sc.hasNextInt()) {
            //     int num1 = sc.nextInt();
            //     System.out.println(num1);
            //     pw.println(num1);
            // }

            // String txt1 = sc.next();
            // System.out.println(txt1);
            // pw.println(txt1);

            // String txt2 = sc.nextLine();
            // System.out.println(txt2);
            // pw.println(txt2);

            int max = 0;
            while (sc2.hasNextLine()) {
                String[] line = sc2.nextLine().split(" ");
                if (Integer.parseInt(line[1]) > max) {
                    max = Integer.parseInt(line[1]);
                }
            }
            System.out.println("max: " + max);
        } // sc.close(); // nem javasolt, helyette try
    }
}

// - kié a legnagyobb?
// - kié a legkisebb?
// - értékek szerint sorrendben a nevek