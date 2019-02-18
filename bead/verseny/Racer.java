package verseny;

public class Racer {
    public final Breed breed;
    public String clientName;
    public int position;
    public boolean beingAttacked;

    public Racer(Breed breed, String clientName) {
        this.breed = breed;
        this.clientName = clientName;
        this.position = 0;
        this.beingAttacked = false;
    }

    // @Override
    // public String toString() {
    //     return clientName + " " + position;
    // }
}