package domino;

public class Domino {
    
    private final byte value1;
    private final byte value2;

    public Domino(byte value1, byte value2) {
        this.value1 = value1;
        this.value2 = value2;
    }

    public Domino(String domino) {
        String[] values = domino.split(" ");
        value1 = Byte.parseByte(values[0]);
        value2 = Byte.parseByte(values[1]);
    }

    public byte getValue1() {
        return value1;
    }

    public byte getValue2() {
        return value2;
    }

    @Override
    public String toString() {
        return value1 + " " + value2;
    }

}
