package garden;

import java.io.File;
import java.io.FileNotFoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PlantFactory extends UnicastRemoteObject implements PlantFactoryInterface {

    private String fileName;
    private List<Plant> plants;

    public PlantFactory(String fileName) throws RemoteException {
        super();
        this.fileName = fileName;
        plants = new ArrayList<>();
        readFile(fileName);
    }

    private void readFile(String fileName) {
        try {
            Scanner sc = new Scanner(new File(fileName));
            while(sc.hasNextLine()) {
                String[] plantData = sc.nextLine().split(",");
                plants.add(new Plant(Long.parseLong(plantData[0]), plantData[1], Integer.parseInt(plantData[2]), Integer.parseInt(plantData[3])));
            }
            sc.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public List<Plant> getPlants() {
        return plants;
    }

    @Override
    public Plant getPlant() throws PlantWitheredException {
        Plant plant = plants.get(0);
        plant.waterPlant(System.currentTimeMillis());
        plants.remove(plant);
        return plant;
    }
}
