
import java.util.*;
import java.net.*;
import java.io.*;

public class Szerver3v2b {
    public static void main(String[] args) throws Exception {
        final int PORT = 12345;

        try (
            ServerSocket ss = new ServerSocket(PORT);
            Socket s        = ss.accept();
            Scanner sc      = new Scanner(s.getInputStream(), "utf-8");
            PrintWriter pw  = new PrintWriter(s.getOutputStream());
        ) {
            List<Integer> nums = receiveNums(sc);
            sendNums(nums, pw);
        }
    }

    private static List<Integer> receiveNums(Scanner sc) {
        List<Integer> nums = new ArrayList<>();
        while (sc.hasNextInt()) {
            int num = sc.nextInt();

            if (num == 0)    break;

            nums.add(num);
        }

        return nums;
    }

    private static void sendNums(List<Integer> nums, PrintWriter pw) {
        for (int num : nums) {
            int result = f(num);

            pw.println(result);
            // pw.flush();
        }
        pw.flush();
    }


    private static int f(int num) { return 2 * num + 1; }
}
