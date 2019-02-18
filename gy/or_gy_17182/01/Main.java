import java.util.*;
import java.lang.*;
import java.io.*;

public class Main {
    public static void main(String[] args) {
        // System.out.println("Hello World!");
        // System.out.println(args.length);

        // int s = 0;
        // ArrayList<Integer> even = new ArrayList<>();
        // System.out.println("args:");
        // for (int i = 0; i < args.length; ++i) {
        //     // System.out.print(args[i] + " ");
        //     System.out.println(i + 1 + ". " + args[i]);
        //     s += Integer.parseInt(args[i]);

        //     if (Integer.parseInt(args[i]) % 2 == 0) {
        //         even.add(Integer.parseInt(args[i]));
        //     }
        // }

        // // System.out.println("args reversed:");
        // // for (int i = args.length; i > 0; --i) {
        // //     System.out.print(args[i] + " ");
        // // }

        // System.out.print("args length: ");
        // for (int i = 0; i < args.length; ++i) {
        //     System.out.print(args[i].length() + " ");
        // }
        // System.out.println();

        // System.out.println("args sum: " + s);

        // System.out.print("args even: ");
        // for (int i = 0; i < even.size(); ++i) {
        //     System.out.print(even.get(i) + " ");
        // }
        // System.out.println();

        try {
            PrintWriter pw = new PrintWriter("file.txt", "UTF-8");
            Random rn = new Random();
            for (int i = 0; i < 100; ++i) {
                pw.println(rn.nextInt());
            }
            pw.close();
        } catch(FileNotFoundException e) {
        } catch(UnsupportedEncodingException e) {
        }
    }
}