package gyak5;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Client {

    public static void main(String[] args) throws IOException {
        Socket s = new Socket("localhost", 12345);
        Scanner sc = new Scanner(s.getInputStream());
        PrintWriter pw = new PrintWriter(s.getOutputStream(), true);
        
        String msg;
        String word = "";
        int counter = 0;
        do {
            msg = sc.nextLine();
            counter++;
            if (!(msg.equals("start") && counter > 1)) {
                List<String> letters = Arrays.asList(msg.split(""));
                Collections.shuffle(letters);
                word = "";
                for (String l : letters) {
                    word += l;
                }
                
                pw.println(word);
                System.out.println(msg+", "+word);
            }
            else {
                System.out.println(msg);
            }
            
        } while(!word.equals("start") && !(msg.equals("start") && counter > 1));
        s.close();
    }
    
}
