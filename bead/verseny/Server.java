package verseny;

import java.io.*;
import java.net.*;
import java.util.*;

public class Server {
    public static final String HOST = "localhost";
    public static final int PORT = 2018;

    public static File file;
    public static int minutes;
    public static int fieldLength;

    public static ArrayList<ClientInfo> clients = new ArrayList<>();
    public static ArrayList<Breed> breeds = new ArrayList<>();
    public static ArrayList<Racer> racers = new ArrayList<>();
    public static ArrayList<Racer> finished = new ArrayList<>();

    public static final String LAUNCH = "launch";
    public static final String PROGRESS = "progress";
    public static final String EXIT = "exit";

    public static void main(String args[]) throws Exception {
        /////////////////////////////////////////////////////////////////////////////////////////////
        // input
        /////////////////////////////////////////////////////////////////////////////////////////////
        System.out.println("Processing input...");

        file = new File(args[0]);
        minutes = Integer.parseInt(args[1]);
        fieldLength = Integer.parseInt(args[2]);

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            int n = Integer.parseInt(br.readLine());

            for (int i = 0; i < n; ++i) {
                String line = br.readLine();

                String[] splitLine = line.split(";");
                String name = splitLine[0];
                // Long speed = Long.parseLong(splitLine[1]);
                int speed = Integer.parseInt(splitLine[1]);
                System.out.println(speed);
                Skill skill = Skill.valueOf(splitLine[2]);
                ArrayList<String> animals = new ArrayList<>();

                if (splitLine.length > 3) {
                    animals = new ArrayList<String>(Arrays.asList(splitLine[3].split(",")));
                } else {
                    skill = Skill.NONE;
                }

                Breed breed = new Breed(name, speed, skill, animals);
                breeds.add(breed);
            }
        } catch (FileNotFoundException e) {
            System.out.println("The file does not exist.");
        } catch (IOException e) {
            System.out.println("Incorrect input.");
        }

        for (Breed b : breeds) {
            System.out.println(b.name + " " + b.seconds);
        }

        /////////////////////////////////////////////////////////////////////////////////////////////
        // server
        /////////////////////////////////////////////////////////////////////////////////////////////

        System.out.println("Starting server...");
        System.out.println("Clients can choose from 3 commands: launch <animal>, progress, exit");

        ServerSocket ss = new ServerSocket(PORT);

        while (true) {
            try {
                ClientInfo client = new ClientInfo(ss);
                clients.add(client);
                System.out.println("A new client is connected : " + client);
                System.out.println("Assigning new thread for this client");
                Thread t = new ClientHandler(client);

                t.start();
            } catch (Exception e) {
                // client.close();
                e.printStackTrace();
            } 
        } 
    }
}

class ClientHandler extends Thread {
    final ClientInfo client;

    public ClientHandler(ClientInfo client) {
        this.client = client;
    }

    @Override
    public void run() {
        try {
            client.name = client.read();
            System.out.println(client.name);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("cant save name");
        }

        while (true) {
            try {
                String received = client.read();

                if (received.equals(Server.PROGRESS)) {
                    progress();
                } else if (received.equals(Server.EXIT)) {
                    client.close();
                    // delete racers!!!
                    break;
                } else {
                    // random string !!!!
                    String[] splitLine = received.split(" ");
                    String command = splitLine[0];
                    String animal = splitLine[1];
                    
                    if (!command.equals(Server.LAUNCH)) {
                        System.out.println("Invalid command. Try launch <animal> or progress or exit.");
                        continue;
                    } else {
                        boolean found = false;
                        Breed foundBreed = null;
                        for (Breed b : Server.breeds) {
                            if (animal.equals(b.name)) {
                                found = true;
                                foundBreed = b;
                                break;
                            }
                        }

                        if (found) {
                            launch(foundBreed);
                        } else {
                            System.out.println("No such animal available.");
                            continue;
                        }
                    }
                }
            } catch (NoSuchElementException e) {
                try {
                    client.close();
                    break;
                } catch (Exception ee) {}
            } catch (Exception e) {
                // e.printStackTrace();
            } 
        }
    }

    public void progress() {
        System.out.println("Current progress:");
        for (ClientInfo c : Server.clients) {
            System.out.println(c.name);
        }
    }

    public void launch(Breed breed) {
        System.out.println(client.name + " is launching a(n) " + breed.name + "...");
        for (ClientInfo c : Server.clients) {
            if (c.name == this.client.name && c.animal1 != null && c.animal2 != null) {
                System.out.println("Can't launch more animals.");
            } else {
                Racer racer = new Racer(breed, this.client.name);
                Server.racers.add(racer);

                move(racer); // duration?
            }
        }
    }

    public void move(Racer racer) {
            Timer timer = new Timer();
                TimerTask task = new TimerTask() {
                    public void run() {
                        if (racer.position < Server.fieldLength) { // disconnected client?
                            racer.position++;
                            System.out.println(racer.position);
                            checkPosition(racer);
                        }
                    }
                };
            timer.schedule(task, 0L, (long) racer.breed.seconds * 1000L);
    }

    public void checkPosition(Racer racer) {
        System.out.println("checkPosition() called");
        ArrayList<Racer> racersOnSpot = new ArrayList<>();
        for (Racer r : Server.racers) {
            if (r.position == racer.position) {
                racersOnSpot.add(r);
            }
        }

        if (racer.position == Server.fieldLength) {
            System.out.println(racer.position);
            Server.finished.add(racer);
            System.out.println(racer.clientName + "'s " + racer.breed.name + " has reached the finish line.");
        }

        if (racer.breed.skill == Skill.CATCH) {
            for (Racer r : racersOnSpot) {
                if (racer.clientName != r.clientName &&
                    r.breed.skill != Skill.CATCH &&
                    racer.breed.canCatch(r.breed.name)) {
                    catchAnimal(r, racer);
                }
            }
        }

        if (racer.breed.skill == Skill.SAVE) {
            Racer prey = null;
            Racer predator = null;
            for (Racer r : racersOnSpot) {
                if (r.clientName == racer.clientName && r.beingAttacked) {
                    prey = r;
                }
                if (r.clientName != racer.clientName && r.breed.skill == Skill.CATCH) {
                    predator = r;
                }
            }

            if (prey != null && predator != null) {
                saveAnimal(prey, predator, racer);
            }
        }
    }

    public void catchAnimal(Racer prey, Racer predator) {
        System.out.println(predator.clientName + "'s " + predator.breed.name + " is attacking" + 
                           prey.clientName + "'s " + prey.breed.name + "...");
        prey.beingAttacked = true;
        new Timer().schedule( 
            new TimerTask() {
                @Override
                public void run() {
                    Server.finished.add(prey);
                    System.out.println(prey.clientName + "'s " + prey.breed.name + " has been caught by" + 
                                       predator.clientName + "'s " + predator.breed.name + ".");
                }
            }, 
            5000 
        );
    }

    public void saveAnimal(Racer prey, Racer predator, Racer savior) {
        System.out.println(savior.clientName + "'s " + savior.breed.name + " is attempting to save" + 
                           prey.breed.name + " from" + 
                           predator.clientName + "'s " + predator.breed.name + "...");
        prey.beingAttacked = false;
        Server.finished.add(predator);
        System.out.println(prey.clientName + "'s " + prey.breed.name + " has been saved by" + 
                           savior.breed.name + " from" + 
                           predator.clientName + "'s " + predator.breed.name + ".");
    }
}