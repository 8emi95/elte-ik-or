package gyakorlat8;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;


public class Server extends UnicastRemoteObject implements ServerInterface {
    private Map<Integer, Task> tasks = new HashMap<>();
    
    public Server() throws RemoteException {
        super();
    }
    
    public static void main(String[] args) throws RemoteException {
        Registry reg = LocateRegistry.createRegistry(12345);
        Server server = new Server();
        reg.rebind("myserver", server);
        System.out.println("A szerver elindult.");
    }
    
    @Override
    public synchronized int elkezd(int id) throws RemoteException {
        if (tasks.containsKey(id)) {
            return tasks.get(id).getTaskNumber();
        }
        else {
            Random rand = new Random();
            int number = rand.nextInt(10)+1;
            tasks.put(id, new Task(number));
            System.out.println("Elkezd: azonosito: "+id+", feladat: "+number);
            return number;
        }
    }

    @Override
    public synchronized void befejez(int id) throws RemoteException {
        if (tasks.containsKey(id) && !tasks.get(id).isDone()) {
            tasks.get(id).setDone(true);
            System.out.println("Befejez: azonosito: "+id+", feladat: "+tasks.get(id).getTaskNumber());
        }
        else {
            System.out.println("A "+id+" azonositoju hallgatonak nincs elkezdett feladata!");
        }
    }

    @Override
    public synchronized void kijavit() throws RemoteException {
        Iterator it = tasks.entrySet().iterator();
        while(it.hasNext()) {
            Map.Entry<Integer, Task> entry = (Map.Entry<Integer, Task>)it.next();
            if (entry.getValue().isDone()) {
                System.out.println("Kijavit: azonosito: "+entry.getKey()+", feladat: "+entry.getValue());
                it.remove();
            }
        }
    }
    
    class Task {
        private int taskNumber;
        private boolean done;
        
        public Task(int taskNumber) {
            this.taskNumber = taskNumber;
            this.done = false;
        }

        public int getTaskNumber() {
            return taskNumber;
        }

        public boolean isDone() {
            return done;
        }

        public void setTaskNumber(int taskNumber) {
            this.taskNumber = taskNumber;
        }

        public void setDone(boolean done) {
            this.done = done;
        }
    }
}
