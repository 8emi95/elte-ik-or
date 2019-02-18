package garden;

import java.io.Serializable;

public class Plant implements Serializable {
    private long ID;
    private String species;
    private int price;
    private int wateringFrequencyInSeconds;
    private long timeOfLastWatering;

    public Plant(long ID, String species, int price, int wateringFrequencyInSeconds) {
        this.ID = ID;
        this.species = species;
        this.price = price;
        this.wateringFrequencyInSeconds = wateringFrequencyInSeconds;
    }

    public void waterPlant(long timeOfLastWatering) throws PlantWitheredException {
        if (this.timeOfLastWatering != 0 && isWithered()) {
            throw new PlantWitheredException("Plant withered.");
        }
        this.timeOfLastWatering = timeOfLastWatering;
    }

    public long getID() {
        return ID;
    }

    public String getSpecies() {
        return species;
    }

    public int getPrice() {
        return price;
    }

    public int getWateringFrequencyInSeconds() {
        return wateringFrequencyInSeconds;
    }

    public long getTimeOfLastWatering() {
        return timeOfLastWatering;
    }

    public boolean isWithered() {
        return (System.currentTimeMillis() - timeOfLastWatering) > (1000 * wateringFrequencyInSeconds);
    }
}
