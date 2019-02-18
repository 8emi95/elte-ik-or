import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class LottoDeploy {
 
    public static void main(String[] args) throws RemoteException {
        args = new String[2];
        args[0] = Integer.toString(90);
        args[1] = Integer.toString(5);
        int numbers = Integer.parseInt(args[0]);
        int winnerNumbers = Integer.parseInt(args[1]);
        Registry reg = LocateRegistry.createRegistry(12345);
        Random rand = new Random();
        List<Integer> winners = new ArrayList<>();
        for (int i=0; i<winnerNumbers; i++) {
            int num = rand.nextInt(numbers)+1;
            while (winners.contains(num)) {
                num = rand.nextInt(numbers)+1;
            }
            winners.add(num);
        }
        for (int i=1; i<=numbers; i++) {
            LottoImpl li = new LottoImpl();
            if (winners.contains(i)) {
                li.setWinner(true);
            }
            else {
                li.setWinner(false);
            }
            reg.rebind(Integer.toString(i), li);
        }
        
    }
}
