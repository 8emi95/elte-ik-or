package gyakorlat8;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Client {
    
    public static void main(String[] args) throws RemoteException, NotBoundException {
        Registry reg = LocateRegistry.getRegistry(12345);
        ServerInterface si = (ServerInterface)reg.lookup("myserver");
        for (int i=0; i<5; i++) {
            new StudentThread(i, si).start();
        }
        new TeacherThread(si).start();
    }
}

class StudentThread extends Thread {
    private int id;
    private ServerInterface si;
    
    public StudentThread(int id, ServerInterface si) {
        this.id = id;
        this.si = si;
    }
    
    @Override
    public void run() {
        Random rand = new Random();
        for (int i=0; i<8; i++) {
            try {
                if (rand.nextBoolean()) {
                    si.elkezd(id);
                }
                else {
                    si.befejez(id);
                }
                Thread.sleep((rand.nextInt(2)+1)*1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(StudentThread.class.getName()).log(Level.SEVERE, null, ex);
            } catch (RemoteException ex) {
                Logger.getLogger(StudentThread.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}

class TeacherThread extends Thread {
    private ServerInterface si;
    
    public TeacherThread(ServerInterface si) {
        this.si = si;
    }
    
    @Override
    public void run() {
        Random rand = new Random();
        for (int i=0; i<10; i++) {
            try {
                si.kijavit();
                Thread.sleep((rand.nextInt(3)+1)*1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(StudentThread.class.getName()).log(Level.SEVERE, null, ex);
            } catch (RemoteException ex) {
                Logger.getLogger(StudentThread.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}